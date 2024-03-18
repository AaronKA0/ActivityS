package com.membership.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.memRelation.MemRelation;
import com.memRelation.MemRelationService;
import com.memberreport.model.MemberReportVO;
import com.memberreport.service.MemberReportService;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.service.NotifyNow;
import com.post.Post;
import com.post.PostService;
import com.postreport.model.PostReportVO;
import com.postreport.service.PostReportService;
import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;
import com.venue.model.VenVO;
import com.venue.service.VenService;


@Controller
public class MembershipPageController {
	@Autowired
	MembershipService membershipSvc;
	
	@Autowired
	VenService venSvc;
	
	@Autowired
	VenOrderService venOrderSvc;
	
	@Autowired
	MemberReportService memberReportSvc;
	
	@Autowired
	PostReportService postReportSvc;
	
	@Autowired
	PostService postSvc;
	
	@Autowired
	MemRelationService memRelationSvc;
	
	@Autowired
	NotifyNow notifyNow;
	
	@Autowired
	Gson gson;
	

	
	// member page
	@GetMapping("/member")
	public String member(Model model, HttpSession session, @ModelAttribute MembershipVO member,
			HttpServletRequest request) {

		Integer memId = member.getMemId();
		if (memId != null) {
			if (session.getAttribute("memId").equals(memId)) {
				request.setAttribute("status", "cur");
			} else {
				MembershipVO membershipVO = membershipSvc.getOneMembership(memId);
				if(membershipVO == null) {
					return "/error/404";
				}
				request.setAttribute("status", "visit");
				request.setAttribute("rMemId", memId);
			}

		} else {
			request.setAttribute("status", "cur");
		}
		return "front-end/membership/member";
	}
	
	// member page
	@PostMapping("/mem")
	public String mem(Model model, HttpSession session, @ModelAttribute MembershipVO member,
			HttpServletRequest request) {

		Integer memId = member.getMemId();
		if (memId != null) {
			if (session.getAttribute("memId").equals(memId)) {
				request.setAttribute("status", "cur");
			} else {
				MembershipVO membershipVO = membershipSvc.getOneMembership(memId);
				if(membershipVO == null) {
					return "/error/404";
				}
				request.setAttribute("status", "visit");
				request.setAttribute("rMemId", memId);
			}

		} else {
			request.setAttribute("status", "cur");
		}
		return "front-end/membership/member";
	}

	@RequestMapping("member/getById")
	public @ResponseBody MembershipVO getMember(@RequestBody String json) {
		MembershipVO member = gson.fromJson(json, MembershipVO.class);
		return membershipSvc.getOneMembership(member.getMemId());
	}

	// member chat
	@GetMapping("/member/chat")
	public String chat(Model model, HttpServletRequest request, @ModelAttribute MembershipVO member) {
		Integer memId = member.getMemId();
		if (memId != null) {
			request.setAttribute("rMemId", memId);
		}
		return "front-end/chat/chat";
	}

	@RequestMapping("member/addPost")
	public @ResponseBody Post addPost(@RequestBody String json) {
		Post post = gson.fromJson(json, Post.class);
		
		// send notification of new post to all friends
		Integer memId = post.getMemId();
		MembershipVO member = membershipSvc.getOneMembership(memId);
		List<MemRelation> friends = memRelationSvc.getFriends(memId);

		Stream<MembershipVO> stream =  friends.stream()
		.map(f -> membershipSvc.getOneMembership(f.getMemIdB()));
		
		Set<MembershipVO> friendSet = stream.collect(Collectors.toSet()); 

		notifyNow.sendNotifyNow(friendSet, "一般通知", member.getMemUsername() + "有一個新的貼文" + "《" + post.getPostTitle() + "》");
		return postSvc.addPost(post);
	}

	@RequestMapping("member/editPost")
	public @ResponseBody Post editPost(@RequestBody String json) {
		Post post = gson.fromJson(json, Post.class);
		return postSvc.editPost(post);
	}

	@RequestMapping("member/getPosts")
	public @ResponseBody List<Post> getPosts(@RequestBody String json) {
		MembershipVO member = gson.fromJson(json, MembershipVO.class);
		return postSvc.getPosts(member.getMemId());
	}

	@RequestMapping("member/sendFriendRequest")
	public @ResponseBody MemRelation sendFriendRequest(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.sendFriendRequest(relation);

		// send friend request notification
		MembershipVO memberA = membershipSvc.getOneMembership(relation.getMemIdA());
		MembershipVO memberB = membershipSvc.getOneMembership(relation.getMemIdB());

		Set<MembershipVO> members = new HashSet<MembershipVO>();
		members.add(memberB);
		notifyNow.sendNotifyNow(members, "一般通知", memberA.getMemUsername() + "向您發送了好友請求");

		return relation;
	}

	@RequestMapping("member/acceptFriendRequest")
	public @ResponseBody MemRelation acceptFriendRequest(@RequestBody String json) {
		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.acceptRequest(relation);

		// send friend request accepted notification
		MembershipVO memberA = membershipSvc.getOneMembership(relation.getMemIdA());
		MembershipVO memberB = membershipSvc.getOneMembership(relation.getMemIdB());

		Set<MembershipVO> members = new HashSet<MembershipVO>();
		members.add(memberB);
		notifyNow.sendNotifyNow(members, "一般通知", memberA.getMemUsername() + "已接受您的好友請求");

		return relation;
	}

	@RequestMapping("member/getFriendRequests")
	public @ResponseBody List<MemRelation> getFriendRequests(@RequestBody String json) {
		MembershipVO member = gson.fromJson(json, MembershipVO.class);
		return memRelationSvc.getFriendRequests(member.getMemId());
	}

	@RequestMapping("member/getBlockedMembers")
	public @ResponseBody List<MemRelation> getBlocks(@RequestBody String json) {
		MembershipVO member = gson.fromJson(json, MembershipVO.class);
		return memRelationSvc.getBlocks(member.getMemId());
	}

	@RequestMapping("member/unblock")
	public @ResponseBody MemRelation unblock(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.removeRelation(relation);
		return relation;
	}

	@RequestMapping("member/blockMember")
	public @ResponseBody MemRelation blockMember(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.blockMember(relation);
		return relation;
	}

	@RequestMapping("member/getFriends")
	public @ResponseBody List<MemRelation> getFriends(@RequestBody String json) {
		MembershipVO member = gson.fromJson(json, MembershipVO.class);
		return memRelationSvc.getFriends(member.getMemId());
	}

	@RequestMapping("member/unfriend")
	public @ResponseBody MemRelation unfriend(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.removeRelation(relation);
		return relation;
	}

	@RequestMapping("member/unsendRequest")
	public @ResponseBody MemRelation unsendRequest(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		memRelationSvc.removeRelation(relation);
		return relation;
	}

	@RequestMapping("member/getRelationStatus")
	public @ResponseBody MemRelation getRelationStatus(@RequestBody String json) {

		MemRelation relation = gson.fromJson(json, MemRelation.class);
		return memRelationSvc.getRelationStatus(relation);
	}
	
	@GetMapping("member/notification")
	public String notification() {
		return "/front-end/notify/notifyWebSocket";
	}
	
	@GetMapping("member/venOrder")
	public String getMemVenOrders() {
		return "/front-end/ven/venOrder";
	}
	
	
	@GetMapping("member/venOrderArchive")
	public String venOrderArchive() {
		return "redirect:/member/venOrder#archive";
	}

	@GetMapping("member/friendTab")
	public String displayMemFriends() {
		return "redirect:/member#friends";
	}

	@PostMapping("mem/venOrders")
	public @ResponseBody List<VenOrderVO> getMemOrders(@RequestBody String json) {
//		VenOrderVO venOrder = gson.fromJson(json, VenOrderVO.class);
		VenOrderVO venOrder = null;
		try {
			venOrder = 	new ObjectMapper().readValue(json, VenOrderVO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return venOrderSvc.getMemOrders(venOrder.getMemVO().getMemId());
	}

	@PostMapping("mem/updateVenOrder")
	public @ResponseBody VenOrderVO updateVenOrder(@RequestBody String json) {
//		VenOrderVO venOrder = gson.fromJson(json, VenOrderVO.class);
		VenOrderVO venOrder = null;
		try {
			venOrder = new ObjectMapper().readValue(json, VenOrderVO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		VenOrderVO oldVenOrder = venOrderSvc.getOneVenOrder(venOrder.getVenOrderId());
		if (venOrder.getOrderStatus() != null) {
			oldVenOrder.setOrderStatus(venOrder.getOrderStatus());
		}
		if (venOrder.getVenRating() != null) {
			VenVO ven = venSvc.getOneVen(venOrder.getVenVO().getVenId());
			if (oldVenOrder.getVenRating() == null) {
				ven.setVenRateCount(ven.getVenRateCount() + 1);
				ven.setVenTotRating(ven.getVenTotRating() + venOrder.getVenRating());
			} else {
				ven.setVenTotRating(ven.getVenTotRating() - oldVenOrder.getVenRating() + venOrder.getVenRating());
			}
			try {
				venSvc.updateVen(ven);
			} catch (Exception e) {
				e.printStackTrace();
			}
			oldVenOrder.setVenRating(venOrder.getVenRating());
		}
		venOrderSvc.updateVenOrder(oldVenOrder);
		return oldVenOrder;
	}
	
	
	@GetMapping("ven/getVens")
	public String getVens() {
		return "/back-end/ven/listAllVen";
	}


	@GetMapping("venOrder/active")
	public @ResponseBody List<VenOrderVO> getActiveOrders() {
		return venOrderSvc.getAll();
	}


	@GetMapping("/front_end/venue/browse")
	public String venbrowse(Model model) {
		return "front-end/ven/ven";
	}
	
	
	@RequestMapping("/memberreport/select_page")
	public String memberReportSelectPage() {
		return "back-end/memberreport/select_page";
	}

	@PostMapping("memReport/insert")
	public @ResponseBody MemberReportVO memReportInsert(@RequestBody String json)  {
		MemberReportVO memberReportVO = gson.fromJson(json, MemberReportVO.class);

		memberReportSvc.addMemberReport(memberReportVO);
		
		// send report received notification
		MembershipVO memberA = membershipSvc.getOneMembership(memberReportVO.getReporterId());
		MembershipVO memberB = membershipSvc.getOneMembership(memberReportVO.getReporteeId());
		
		Set<MembershipVO> members = new HashSet<MembershipVO>();
		members.add(memberA);
		notifyNow.sendNotifyNow(members, "系統通知", memberA.getMemUsername() + "，我們已收到並會調查您對" + memberB.getMemUsername() + "的檢舉");
		
		return memberReportVO;
	}

	@RequestMapping("/postreport/select_page")
	public String postReportSelectPage() {
		return "back-end/postreport/select_page";
	}
	
	@PostMapping("postReport/insertPost")
	public @ResponseBody PostReportVO memReportInsertPost(@RequestBody String json)  {
		PostReportVO postReportVO = gson.fromJson(json, PostReportVO.class);

		postReportSvc.addPostReport(postReportVO);
		
		// send report received notification
		MembershipVO memberA = membershipSvc.getOneMembership(postReportVO.getMemId());
		MembershipVO memberB = membershipSvc.getOneMembership(postReportVO.getReporteeId());
		
		Set<MembershipVO> members = new HashSet<MembershipVO>();
		members.add(memberA);
		notifyNow.sendNotifyNow(members, "系統通知", memberA.getMemUsername() + "，我們已收到並會調查您對" + memberB.getMemUsername() + "的貼文檢舉");
		
		return postReportVO;
	}
	
	@RequestMapping("postreport/listAllPostReport")
	public String listAllPostReport() {
		return "back-end/postreport/listAllPostReport";
	}
	
	@PostMapping("/memberreport/getReport")
	public @ResponseBody MemberReportVO getMemReport(@RequestBody String json) {
		MemberReportVO memberReportVO = gson.fromJson(json, MemberReportVO.class);
		return memberReportSvc.getReport(memberReportVO.getReporterId(), memberReportVO.getReporteeId());
	}
	
	@PostMapping("/postreport/getReport")
	public @ResponseBody List<PostReportVO> getPostReport(@RequestBody String json) {
		PostReportVO postReportVO = gson.fromJson(json, PostReportVO.class);
		return postReportSvc.getReport(postReportVO.getMemId(), postReportVO.getReporteeId());
	}


}
