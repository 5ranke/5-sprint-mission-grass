## 요구사항

### 기본
- [x] 프로젝트 초기화
- [x] 도메인 모델링
- [x] 서비스 설계 및 구현
- [x] 메인 클래스 구현
- [x] 기본 요구사항 커밋 태그

### 심화
- [x] 서비스 간 의존성 주입 (삭제 및 수정 권한 확인)

### 스크린샷

<img width="1847" height="590" alt="image" src="https://github.com/user-attachments/assets/543037a2-7353-479d-bf07-cd58851a8d75" />

- **UserService**
    - CRUD 구현
    - 사용자 검색 기능 (이름, 이메일)


<img width="1853" height="588" alt="image" src="https://github.com/user-attachments/assets/756d61f9-0793-48a8-8c36-1b43c7642679" />

- **MessageService**
    - CRUD 구현
    - 채널별 메시지 분류하여 읽어오기 -> 채널 서비스에 들어가야 하나?
    - 메시지 검색 기능


<img width="1848" height="790" alt="image" src="https://github.com/user-attachments/assets/214480e0-e7bf-47b1-93fe-20d73448379c" />

- **ChannelService**
    - CRUD 구현
    - 채널별 멤버 읽어오기
    - 채널 검색 기능
    - 멤버 추가, 멤버 삭제 기능 (채널별 멤버 관리)
