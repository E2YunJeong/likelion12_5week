package org.example.manageproject.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import org.example.manageproject.domain.Member;
import org.example.manageproject.Service.MemberService;
import org.example.manageproject.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<MemberDto> registerMember(@RequestBody MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());

        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(MemberDto.from(registeredMember));
    }

    /*-------------------------------------------------------------------------*/
    // 조회
    // 모든 회원 조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        List<Member> members = memberService.getAllMembers();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members){
            MemberDto dto = MemberDto.from(member);
            memberDtos.add(dto);
        }
        return ResponseEntity.ok(memberDtos);
    }

    // ID로 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable(name="id") Long id) {
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()) {
            MemberDto dto = MemberDto.from(memberOptional.get());
            return ResponseEntity.ok(dto);
            // 200 return
        }
        else{
            return ResponseEntity.notFound().build();
            // 404 not found return
        }
    }
    /*-------------------------------------------------------------------------*/

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name="id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
        // 204 No Content return (요청 성공적, 반환할 컨텐츠 없음)
    }

    // 회원 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<MemberDto> UpdateMember(@PathVariable(name="id") Long id, @RequestBody MemberDto memberDto) {
        try {
            Member updatedMember = memberService.updateMember(id, memberDto);
            return ResponseEntity.ok(MemberDto.from(updatedMember));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}