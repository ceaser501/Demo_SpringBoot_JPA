package jpabook.jpashop.MemberService;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 컴포넌트 스캔 대상이 되어 자동으로 Spring bean에 등록
@Service
@Transactional(readOnly = true)                     // 읽기전용이라고 명시, 성능이 조금 더 좋아지는 이점이 있음
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // @Autowired
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional                                      // 쓰기도 가능하므로, 다시 선언해 주어서 readOnly = true에 해당 안되게 함
    public Long join(Member member){
        validateDuplicateMember(member);                // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByNames(member.getName());
        // 동시 접근 고려 해야 함 -> 멀티스레드 환경에서 디비에 member name을 유니크 제약조건으로 잡기를 권장
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체조회 (전체조회)
     * @param
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 단건조회
     * @param
     * @return
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
