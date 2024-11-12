# java-convenience-store-precourse

## 기능 목록
1. ### 편의점 관련 기능
   - #### 편의점 정보 로드
     - `products.md`의 재고 정보 로드
     - `promotions.md`의 재고 정보 로드
   - #### 구매 가능 조회
     - 제품이 존재하는 지 확인한다.
     - 재고가 충분한 지 확인한다.
   - #### 제품 구매
     - 제품 구매에 따른 재고를 업데이트 한다.

2. ### 할인 관련 기능
   - #### 프로모션 적용 계산
     - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
     - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.
     - 프로모션 관련 입력에 따른 전체 구매 상품 리스트를 생성한다.
   - #### 멤버쉽 적용 계산
     - 멤버십 할인 적용 여부를 입력 받는다.

3. ### 결제 관련 기능
   - #### 결제 정보 계산
     - `전체 상품의 금액`
     - `프로모션 할인 금액`
     - `멤버십 할인 금액`
     - `전체 결제 금액`
   - #### 영수증 발행
       - `전체 구매 상품 정보`
       - `프로모션 할인 상품 정보`
       - `결제 정보`
   
