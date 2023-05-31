package com.example.genie.domain.pot.service;

import com.example.genie.common.util.UserUtils;
import com.example.genie.domain.pot.entity.Pot;
import com.example.genie.domain.pot.form.PotCreateForm;
import com.example.genie.domain.pot.repository.PotRepository;
import com.example.genie.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class PotService {

    private final PotRepository potRepository;

    private final UserUtils userUtils;

    public void createPot(Authentication authentication, PotCreateForm potCreateForm, BindingResult bindingResult) {
        User user = userUtils.getUser(authentication);
        Pot pot = Pot.createPot(potCreateForm, user.getId());
        potRepository.save(pot);
    }

}