package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        // @Valid 쓰면 javax.validation 쓰겠다는 뜻! MemberForm.java에 @NotNull 같은 것들..
        // Member Entity를 쓰지 않고, form을 새로 생성해서 new로 model에 담아 넘기는 이유는, 꼭 필요한 것만 가져다 쓰기 위함이다.
        // Member를 그대로 쓴다면, 불필요한 변수들도 있고 validation을 MemberEntity에 작성하면 소스가 지저분해 질 수 있기 때문이다.
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
