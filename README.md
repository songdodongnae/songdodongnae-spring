# 🌎 송도의 모든 순간, 송도 동네

<img width="1966" height="1072" alt="image" src="https://github.com/user-attachments/assets/01b0bd7c-3ee1-4b7d-b493-04cb5e8d292c" />

## 🟨 송도동네 웹 서비스 소개
> **“한 눈에 송도의 모든 순간을, 송도동네”**

송도동네는 **송도 지역의 맛집, 카페, 축제 등 고품질 로컬 콘텐츠를 소개하는 웹 서비스**입니다.
우리는 직접 발로 뛰며 촬영하고 취재한 실제 정보를 제공하여, 신뢰할 수 있는 지역 큐레이션 플랫폼을 지향합니다.

### 🗂 큐레이션 기능
- **비슷한 분위기의 맛집/카페/축제를 하나의 큐레이션으로 묶어 제공**합니다.
- 예:
  - ```🌿 브런치가 맛있는 초록 감성 카페 TOP 5```
  - ```🎉 연인과 가기 좋은 감성 축제 추천```
  - ```🍜 밥 한끼 뚝딱, 가성비 맛집 모음```

### 🧑‍🎨 크리에이터 기반 큐레이션
- 사용자는 **크리에이터의 취향을 기반으로 큐레이션된 콘텐츠를 탐색**할 수 있습니다.
- 예를 들어 “감성 카페를 좋아하는 크리에이터 A”가 추천한 콘텐츠들을 모아보거나,
“한식 맛집을 즐겨 찾는 크리에이터 B”의 추천 맛집만 골라볼 수 있습니다.

### 🔍 핵심 가치
- **정보의 신뢰도**: 모든 콘텐츠는 직접 촬영 및 검증된 정보를 바탕으로 제공됩니다.
- **취향 기반 추천**: 각기 다른 크리에이터의 취향을 따라가며, 나만의 맞춤 송도 탐방이 가능합니다.
- **경험 중심 소개**: 단순 정보 나열이 아닌, 경험과 분위기를 전달하는 에디터형 콘텐츠를 제공합니다.

</br>

## 🧑‍💻 팀원 소개

<div align=center>

|      오승언      |                                                           김희영                                                           |                                                                                                      최서영                 |
| :------------: |:-----------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------:|
| <a href="https://github.com/suee97"> <img src="https://avatars.githubusercontent.com/u/55964078?v=4" width=150px alt="_"/> </a> | <a href="https://github.com/Huiyeongkim"> <img src="https://avatars.githubusercontent.com/u/146138986?v=4" width=150px alt="_"/> </a> | <a href="https://github.com/choiyoung69"> <img src="https://avatars.githubusercontent.com/u/122353155?v=4" width=150px alt="_"/> </a> |
| BE |                                                         BE                                                          |                                                          BE                                                           | 

</div>

</br>

## 📦 Directory Structure
```
src/main/java
└── com
    └── culturefinder
        └── songdodongnae
            ├── bookmark
            ├── creator
            ├── curation
            ├── delicious_spot
            ├── exception
            ├── festival
            ├── mypage
            ├── s3
            ├── search
            ├── series
            ├── user
            └── utils
```

</br>

## 🔗 Dependency
<p align="center">
  <img src="https://github.com/user-attachments/assets/a4fd20a0-5380-4222-8f4a-d6cad1f878b9" width="500" />
</p>

- ☕ **Java 21**: 최신 LTS 버전으로 성능 개선
- 🛠️ **Gradle**: 빌드 및 의존성 관리
- 🌱 **Spring Boot 3**: 간결하고 확장성 있는 백엔드 프레임워크
- 🧩 **JPA + JPQL**: ORM 기반 데이터 처리
- 🐬 **MySQL 8**: 안정적인 관계형 데이터베이스
- 📄 **Swagger**: REST API 명세 문서 자동화

</br>

## 🗂️ Database 설계
<img width="2868" height="1834" alt="image" src="https://github.com/user-attachments/assets/c3494fcf-defc-4115-a2e0-ee41747eea3f" />
