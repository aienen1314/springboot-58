package cn.edu.swjtu.demo.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.swjtu.demo.Pojo.CarInfoWithBLOBs;
import cn.edu.swjtu.demo.Pojo.UserInfo;
import cn.edu.swjtu.demo.Service.UserService;

@RestController
public class LoginController {
	@Autowired
	UserService userService;

	// 用户登录
	@PostMapping(value = "/login")
	@ResponseBody
	public UserInfo login(@RequestBody Map<String, Object> login, HttpSession session) {
		String username = (String) login.get("username");
		String password = (String) login.get("password");
//		return new Login(counter.incrementAndGet(), String.format(template, id));
//		返回json
//		ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
//		mav.addObject("loginUrl", "www.baidu.com");
		if (userService.login(username, password)) {
			System.out.println("password match");
			UserInfo userInfo = userService.getUserInfo(username);
			session.setAttribute("user", userInfo);
			return userInfo;
		}
		return new UserInfo();
	}

	@GetMapping(value = "/showPosts")
	@ResponseBody
	public List<CarInfoWithBLOBs> showPosts() {
		return userService.showPosts();
	}

	// 用户退出
	@GetMapping(value = "/logout")
	@ResponseBody
	public boolean logout(HttpSession session) {
		session.invalidate();
		return true;
	}
}
