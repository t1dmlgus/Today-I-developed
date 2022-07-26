package dev.t1dmlgus.moviemvp.reservation.common.util;

import dev.t1dmlgus.moviemvp.reservation.common.exception.ErrorType;
import dev.t1dmlgus.moviemvp.reservation.domain.Cinema;
import dev.t1dmlgus.moviemvp.reservation.common.exception.NotValidException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * class : 토큰 생성 Util
 * version 1.0
 * ==================================================
 * DATE                 DEVELOPER   NOTE
 * ==================================================
 * 2022-07-26           이의현        영화관토큰 생성로직
 * 2022-07-26           이의현        상영관토큰 생성로직
 * 2022-07-26           이의현        지역 별 토큰타입(areaCode) 조회
 *
 */

public class TokenUtil {

    public static String generateCinemaToken(String area) {

        String areaCode = TheaterAreaType.getAreaCode(area);// 1
        HashMap<String, Cinema> cinemaInstances = Cinema.cinemaInstances;

        long count = cinemaInstances.values().stream()
                .filter(i -> i.getCinemaToken().substring(0, 1).equals(areaCode))
                .count();
        return areaCode + String.format("%03d", count+1);
    }

    public static String generateTheaterToken(String cinemaToken, int index) {
        String theaterNum = String.format("%02d", index);
        return cinemaToken + theaterNum;
    }

    @Getter
    @RequiredArgsConstructor
    public static enum TheaterAreaType {

        SEOUL("1", "서울"),
        GYEONGGIDO_INCHEON("3", "경기/인천"),
        CHUNGCHEONG_DAEJEON("4", "충청/대전"),
        JEOLLA_GWANGJU("5", "전라/광주"),
        GYEONGBUK_DAEGU("6", "경북/대구"),
        GYEONGNAM_BUSAN_ULSAN("7", "경남/부산/울산"),
        GANGWON("8", "강원"),
        JEJU("9", "제주");

        private final String areaCode;
        private final String areaName;

        public static String getAreaCode(String areaName) {
            return Arrays.stream(TheaterAreaType.values())
                    .filter(i -> i.getAreaName().equals(areaName))
                    .map(TheaterAreaType::getAreaCode)
                    .findFirst()
                    .orElseThrow(()-> new NotValidException(ErrorType.AREA_INVALID_PARAMETER));
        }
    }
}