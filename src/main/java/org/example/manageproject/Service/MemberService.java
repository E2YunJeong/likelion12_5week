package org.example.manageproject.Service;

import org.example.manageproject.Repository.MemberRepository;
import org.example.manageproject.domain.Member;
import org.example.manageproject.dto.MemberDto;
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

    // Create 메서드
    @Transactional
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    /*-------------------------------------------------------------------------*/
    // Read 메서드
    // 전체 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 특정 데이터(id)만 조회
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
    /*-------------------------------------------------------------------------*/

    // DELETE 메서드
    @Transactional
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }
    // 해당 id값을 가진 리소스만 제거하면 됨 -> id 외에 주고받을 데이터 필요 X -> DTO 필요 X

    // UPDATE 메서드
    @Transactional
    public Member updateMember(Long id, MemberDto memberDto) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (memberDto.getName() != null) {
                member.setName(memberDto.getName());
            }
            if (memberDto.getEmail() != null) {
                member.setEmail(memberDto.getEmail());
            }
            return memberRepository.save(member);
        }
        else{
            throw new RuntimeException("Member not found with id " + id);
        }
    }
}