package com.act.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AllController {
	
    @RequestMapping("")
    public String index(){
        return "front-end/zuo-huo";
    }

    @RequestMapping("/act/createAct")
    public String createAct(){
        return "front-end/act/createAct";
    }

    @RequestMapping("/act/updateAct")
    public String updateAct(){
        return "front-end/act/updateAct";
    }

    @RequestMapping("/act/joinAct")
    public String joinAct(){
        return "front-end/act/joinAct";
    }
    
    @RequestMapping("/membership/commentreport") //要改
    public String commentReport() {
     return "back-end/report/commentReport";
    }

    @RequestMapping("/membership/report") //要改
    public String report() {
     return "back-end/report/report";
    }

    @RequestMapping("/membership/actregistration")
    public String actreg() {
     return "front-end/actregistration/actRegistration";
    }

    @RequestMapping("/membership/actfollow")
    public String actfollow() {
     return "front-end/actfollow/actFollow";
    }

    @RequestMapping("/membership/actreview")
    public String actreview() {
     return "front-end/actreview/actReview";
    }
    
    @GetMapping("/Zuo-Huo")
    public String home(Model model) {
        return "front-end/zuo-huo";
    }
	
	@GetMapping("/membership/logging")
    public String login(Model model) {
        return "front-end/membership/login";
    }	
	
	@GetMapping("/front_end/notify")
    public String notify(Model model) {
        return "front-end/notify/notify";
    }
	
	@GetMapping("/front_end/venue/venue")
    public String rent(Model model) {
        return "front-end/venue/venue";
    }
	
	
	//******************************* 後 台 back-end ******************************* //
	
	@GetMapping("/emp_Index")
	public String emp_Index(Model model) {
	    return "back-end/emp_Index";
	}
		
	
	// 會員管理
	@GetMapping("/membership/select_page")
    public String select_page1(Model model) {
        return "back-end/membership/select_page";
    }

	@GetMapping("/membership/listAllMembership")
    public String listAllMembership(Model model) {
        return "back-end/membership/listAllMembership";
    }
	
	
	// 公告管理
	@GetMapping("/back_end/announcement/ann_page")
	public String ann_page(Model model) {
	    return "back-end/announcement/ann_page";
	}
		
	
	// 訊息管理
	@GetMapping("/back_end/notify/notify_page")
	public String notify_page(Model model) {
	    return "back-end/notify/notify_page";
	}		
	
	
	// 場地預訂管理
	@GetMapping("/back_end/ven-order/ven_order_page")
    public String ven_order_page(Model model) {
        return "back-end/ven-order/ven_order_page";
    }
	
	
	// 場地狀態管理
	@GetMapping("/back_end/ven-closed/ven_closed_date")
	public String ven_closed_date(Model model) {
	    return "back-end/ven-closed/ven_closed_date";
	}
}
