package org.example.project2.domain.restaurants.service;

import lombok.RequiredArgsConstructor;
import org.example.project2.domain.restaurants.repository.RestaurantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantsService {
    // 로그인 필요한 함수는 PrincipalDetails 를 통해 유저 정보 가져오기
    private final RestaurantsRepository restaurantsRepository;




}
