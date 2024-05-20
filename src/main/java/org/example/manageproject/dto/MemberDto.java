package org.example.manageproject.dto;

import lombok.*;
import org.example.manageproject.domain.Member;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registerDate;


    public static MemberDto from(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setRegisterDate(member.getRegisterDate());
        return memberDto;
    }
}