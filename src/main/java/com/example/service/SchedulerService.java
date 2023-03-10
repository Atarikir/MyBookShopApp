package com.example.service;

import com.example.data.user.UserEntity;
import com.example.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

  private final UserRepository userRepository;

  //    @Scheduled(fixedRate = 30000)
  @Scheduled(cron = "@daily")
  public void deleteAnonymousUser() {
    List<UserEntity> userAnonList = userRepository.findByNameAndRegTimeBefore("anonymousUser",
        UtilityService.getTimeNowUTC().minusMonths(1));
//    log.info("userAnonList - " + userAnonList);
    userRepository.deleteAll(userAnonList);
  }

//  @Scheduled(fixedRate = 30000)
//  public void deleteAnonUser() {
//    List<UserEntity> userAnonList = userRepository.findByNameAndRegTimeBefore("anonymousUser",
//        UtilityService.getTimeNowUTC().minusMinutes(1));
//    log.info("userAnonList - " + userAnonList);
//    userRepository.deleteAll(userAnonList);
//  }
}
