# 🍽 식당 예약 시스템 (Restaurant Reservation System)

| 구분 | 내용 |
|------|------|
| **교육기관** | KGITBANK |
| **과정명** | 핀테크 개발을 위한 풀스택 개발자 양성과정 |
| **교육기간** | 2025.03.10 ~ 2025.09.09 |
| **프로젝트명** | 식당 예약 시스템 (Restaurant Reservation System) |
| **개발기간** | 2025.04.14 ~ 2025.04.28 (총 15일) |
| **참여인원** | 5명 |
| **담당업무** | DB 테이블 및 ERD 설계 (25%)<br>예약 시스템 구현 (50%)<br>발표 자료 제작 (25%) |
| **개발환경** | Eclipse 22.09, Oracle 18c |
| **사용기술** | Java GUI (Swing), JDBC |
| **관련 링크** | (https://blog.naver.com/pr1010327/224026719927) |

---

### 📘 개요
- 식당 정보 조회, 원하는 식당의 메뉴 예약, 시간, 인원, 예약자 정보를 활용하여 **Java GUI 기반 식당 예약 프로그램** 구현  
- **노쇼 방지**를 위해 식당 관리자가 예약을 승인/거절할 수 있도록 설계  

---

### 💡 기획 의도
| 목적 | 설명 |
|------|------|
| **편의성 향상** | 힘든 웨이팅 없이 원하는 시간에 미리 예약 가능 |
| **예약 기능 구현** | 사용자가 원하는 시간에 맞춰 예약 가능하도록 설계 |
| **시간 제한 로직** | 현재 시간 기준으로 한 시간 후부터 예약 가능 |
| **당일 예약 지원** | 오늘 날짜로도 예약이 가능하도록 구현 |
| **즉흥 예약 가능** | 갑작스럽게 특정 식당 메뉴가 먹고 싶을 때 기다림 없이 예약 후 방문 가능 |

---

<details>
  <summary>📸 사진 더보기</summary>

  
 <p align='center'> <img width="754" height="838" alt="image" src="https://github.com/user-attachments/assets/9908a3b0-5201-403d-b7b8-b45296a8b37e" /></p>


   <p align='center'><b>사용자 페이지</b></p>
  <p align='center'><img width="589" height="621" alt="KakaoTalk_20250425_194835592_01" src="https://github.com/user-attachments/assets/c8ff8daf-6e10-46bd-9777-aaa4a5dcbe57" /></p>

<p align='center'><img width="954" height="860" alt="image" src="https://github.com/user-attachments/assets/307fdba9-2194-4cf5-b610-b0efbbeaccf5" /></p>

<p align='center'><img width="492" height="480" alt="image" src="https://github.com/user-attachments/assets/9f7d1e21-1fd6-4013-aee1-5b6347eab2ef" /></p>

<p align='center'><img width="493" height="512" alt="image" src="https://github.com/user-attachments/assets/fef7f7b1-9f05-4552-841d-58fa363a7536" /></p>

<p align='center'><img width="495" height="531" alt="image" src="https://github.com/user-attachments/assets/058e082d-a7a4-4083-b893-fe8eeeb11cb5" /></p>

<p align='center'><img width="1123" height="525" alt="image" src="https://github.com/user-attachments/assets/6caaa6e9-30fc-46d5-bc03-b7a3967db748" /></p>

---
<br><br><br>

<p align='center'><b>관리자 페이지</b></p>
<p align='center'><img width="736" height="772" alt="image" src="https://github.com/user-attachments/assets/865b25f0-c646-418f-bba4-c77dfe25866e" /></p>

<p align='center'><img width="800" height="854" alt="image" src="https://github.com/user-attachments/assets/a7b27228-8ffd-43d1-8611-7cc928bde06a" /></p>

<p align='center'><img width="1150" height="508" alt="image" src="https://github.com/user-attachments/assets/0480f89c-feb8-440d-a5e8-dd6fb35b93f7" /></p>

<p align='center'><img width="1125" height="504" alt="image" src="https://github.com/user-attachments/assets/fbef50d9-dc3f-49ab-b10b-3f83e4d84701" /></p>

---



  

</details>

### 👥 계정 구조 설명
- **관리자 계정**
  - 가게 등록 / 수정 / 삭제 / 조회 기능
  - 메뉴 등록 / 수정 / 삭제 / 조회 기능
  - 예약 승인 / 거절 기능 (노쇼 방지 목적)
- **사용자 계정**
  - 식당 조회 및 메뉴 조회
  - 원하는 식당과 메뉴를 선택 후 예약 가능
  - 예약 시 인원 수, 시간, 이름, 연락처 입력

  ---
### 👥 담당 역할
- **담당 역할 (본인)**
  - 예약 기능 구현 전담
  - **운영 시간 제약 로직 설계**
    - 가게의 **운영 시작시간 / 마감시간 / 라스트오더 시간**을 지정하여  
      해당 시간대 내에서만 예약이 가능하도록 구현
    - 당일 예약 가능하도록 구현(단 한시간 이전의 예약은 비활성화)  
    - 라스트오더 시간이 있을 경우, 지정된 시간까지만 예약 가능하도록 제한  
    - 핵심 목표: **운영 시간 외 예약 불가 시스템 구현**

---

### 🧩 설계 및 구조
예약 데이터의 흐름은 다음과 같습니다 👇  

| 단계 | 기능 내용 |
|------|------------|
| 1 | 예약하려는 **가게 정보** 선택 |
| 2 | **예약 메뉴 및 수량** 입력 |
| 3 | **예약자 이름 / 전화번호 / 방문 인원 / 예약일자 / 예약시간** 입력 |
| 4 | **예약 신청 완료 시**, 해당 내용이 **관리자 예약 승인 창으로 전달** |
| 5 | 관리자는 예약 요청을 확인하고 **승인 / 거절 선택 가능** |

---

### 🗂️ 설계 자료 (예시)
- **ERD / 설계도:**  
  - 예약 테이블(`reservation`)  
  - 메뉴 테이블(`menu`)  
  - 사용자 테이블(`user`)  
  - 관리자 테이블(`admin`)  
  - 각 테이블은 예약 정보(식당, 메뉴, 수량, 인원, 예약시간 등)를 중심으로 관계 설정  

---

### 🎯 구현 목표 요약
- ✅ 운영 시간 내 예약만 가능  
- ✅ 라스트오더 시간까지 예약 제한  
- ✅ 관리자 승인/거절 기능으로 노쇼 방지  
- ✅ 사용자 편의 중심의 GUI 예약 인터페이스 구현
