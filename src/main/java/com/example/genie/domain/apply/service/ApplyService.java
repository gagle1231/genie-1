package com.example.genie.domain.apply.service;

import com.example.genie.domain.apply.entity.Apply;
import com.example.genie.domain.apply.entity.State;
import com.example.genie.domain.apply.exception.PotAlreadyFullException;
import com.example.genie.domain.apply.repository.ApplyRepository;
import com.example.genie.domain.pot.entity.Pot;
import com.example.genie.domain.pot.repository.PotRepository;
import com.example.genie.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplyService {
    
    ApplyRepository applyRepository;
    PotRepository potRepository;
    //Apply 생성
    public Apply createApply(User user, Long potId){
        Pot pot = potRepository.findById(potId).orElseThrow(() -> new EntityNotFoundException("Pot not found"));
        Apply apply = Apply.builder().state(State.APPLY).applicant(user).pot(pot).build();
        applyRepository.save(apply);
        return apply;
    }

    public List<User> getApplyUserList(Long potId){
        List<Apply> appyList = applyRepository.findByPot_Id(potId);
        List<User> userList = new ArrayList<>();
        for(Apply apply : appyList){
            userList.add(apply.getApplicant());
        }
        return userList;
    }


    @Transactional
    public void appoveApply(Long potId, Long userId, int state) throws PotAlreadyFullException {
        State s;
        s = (state == 0 ? State.APPROVED : State.REJECTED);
        //1. Pot의 remain(남은 인원) 체크
        Apply apply = applyRepository.findByPot_IdAndApplicant_Id(potId, userId);
        Pot pot = apply.getPot();
        if(pot.getRemain() <= 0){
            throw new PotAlreadyFullException(pot.getPotName());
        }
        //2. 승인이면 remain 1 줄이기
        if(s.equals(State.APPROVED)) {
            pot.apporveUser();
        }

        //3. apply 상태를 바꾸기
        apply.changeState(s);
        applyRepository.save(apply);
    }

    public List<Apply> getApprovedApplyList(Pot pot){
        List<Apply> applyList = applyRepository.findByStateAndPot(State.REJECTED, pot);
        return applyList;
    }


}
