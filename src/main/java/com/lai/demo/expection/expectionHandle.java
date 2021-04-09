package com.lai.demo.expection;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lai.demo.commons.dto.resResult;

/**
 * springboot全局异常处理
 * @ControllerAdvice:
 * 		是controller的一个辅助类，最常用的就是作为全局异常处理的切面类，可以指定扫描范围
 * 		约定了几种可行的返回值:
 * 			返回String: 表示跳到某个view
 * 			返回modelAndView:可以指定返回哪个页面+携带哪些参数
 * 			返回model: 即map或其他自定义类,需要使用@ResponseBody进行json转换后返回
 * 如果全部异常处理返回json，可以使用 @RestControllerAdvice 代替 @ControllerAdvice 
 * 这样在方法上就可以不需要添加 @ResponseBody。
 */
@ControllerAdvice
public class expectionHandle {
	
	/**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	System.out.println("===============@InitBinder==============");
    }

    /**
     * 访问springmvc地址前进入该方法
     * 把值绑定到Model中，在controller有@RequestMapping的方法中可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    	//System.out.println("===============把值绑定到Model中[expectionHandle.addAttributes]==============");
        model.addAttribute("author", "laigq");
    }
    /**
     * 全局异常捕捉处理 (返回json格式)
     * @param ex
     * @return
     * 注解说明
     * @ExceptionHandler:实现自定义异常处理。配置的 value 指定需要拦截的异常类型,下面拦截了 Exception.class 这种异常。
     */
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public resResult handleException(Exception e){
		System.out.println("===============springBoot全局异常处理器捕捉到异常[expectionHandle.handleException]==============");
		//只会捕捉到controller方法抛出的异常，如果service或controller有try捕捉就不会进来该方法
        return resResult.error(e.getMessage());
    }

	/**
	 * 全局异常捕捉处理 (返回指定页面+数据)
	 * 渲染某个页面模板返回给浏览器
	 * @param ex
	 * @return
	 */
	/*@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception ex) {
		System.out.println("===============springBoot全局异常处理器捕捉到异常==============");
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("error");//指定跳转的错误页面
	    modelAndView.addObject("code", "error");
	    modelAndView.addObject("msg", ex.getMessage());
	    return modelAndView;
	}*/
}
