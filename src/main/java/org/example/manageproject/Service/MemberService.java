package org.example.manageproject.Service;

import org.example.manageproject.Repository.MemberRepository;
import org.example.manageproject.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    @Transactional
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }
    //위의 한 줄이면 create 가능, db 저장
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    //db 조회
    
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
    //특정 id만 조회

}