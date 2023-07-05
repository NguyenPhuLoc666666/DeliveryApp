# DeliveryApp
Graduate Project
<h2>Bài toán chia đơn hàng</h2>
<p class="text">Các siêu thị nhận một lượng đơn hàng lớn (n đơn) và cần phân chia số lượng n đơn hàng này cho m người giao hàng trong ca làm một cách tối ưu, tiết kiệm thời gian và chi phí nhất trong khả năng. Đảm bảo mỗi đơn hàng chỉ được giao bởi một người giao hàng. Đảm bảo độ phù hợp của giải pháp (tổng thời gian vận chuyển của tất cả người giao hàng) là tối ưu nhất trong khả năng của thuật toán. Mỗi đơn hàng chỉ được giao bởi một người giao hàng. Điểm xuất phát và điểm kết thúc của mỗi người giao hàng là vị trí của siêu thị. Số lượng đơn hàng của người giao hàng được chia ngẫu nhiên theo số đơn tối thiểu P_min và số đơn tối đa Pmax.</p> 
<ul>Đầu vào của thuật toán:</ul>
	<li>Danh sách giá trị thời gian di chuyển giữa các điểm trong danh sách đơn hàng và vị trí của siêu thị.</li>
	<li>Danh sách thông tin các đơn hàng cần giao trong ca làm.</li>
	<li>Vị trí của siêu thị.</li>
	<li>Số lượng người giao hàng.</li>
	<li>Các thông số thích nghi (được cài đặt mặc định trong thuật toán).</li>
 <img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/f653918b-d497-431d-84b0-404e1c214928"/>
<p>Hình 1 Đầu vào của bài toán chia đơn hàng</p>
<ul>Đầu ra của thuật toán:</ul>
	<li>Một danh sách người giao hàng sẽ đi giao trong ca làm đó, tương ứng với mỗi người giao hàng là một danh sách thông tin các đơn cần giao.</li>
	<li>Tổng thời gian vận chuyển của giải pháp.</li> 
 <img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/e169d679-da15-400f-a72a-bcac2036538a"/>
<p>Hình 2 Đầu ra của thuật toán chia đơn hàng</p>
<h2>Bài toán lựa chọn đơn hàng tối ưu</h2>
<p class="text">Sau khi các đơn hàng đã được thuật toán phân chia, mỗi người giao hàng sẽ nhận một danh sách các đơn hàng mà họ cần phải giao trong ca làm. Khi khởi hành, chạy lại bài toán lựa chọn đơn hàng tối ưu để nhận đề xuất. Trước mỗi lúc giao một đơn hàng người giao hàng phải gọi cho khách hàng để xác nhận lại thời gian và địa điểm chỉnh xác để nhận hàng, nếu khách hàng không thể nhận hàng trong ca làm này, họ có thể hoãn sang một ca khác. Lúc này người giao hàng chủ động thay đổi trạng thái giao hàng của đơn hàng và chạy lại bài toán lựa chọn đơn hàng tối ưu để đề xuất đơn hàng tiếp theo. Đảm bảo độ phù hợp của giải pháp (tổng thời gian vận chuyển) là tối ưu nhất trong khả năng của thuật toán. Người giao hàng phải giao tất cả các đơn có trạng thái cần giao. Điểm xuất phát và điểm kết thúc của người giao hàng là vị trí của siêu thị. Người giao hàng có thể lựa chọn đơn hàng tiếp theo để giao theo đề xuất trong kết quả đầu ra của bài toán lựa chọn đơn hàng tối ưu được thể hiện rõ ở phụ lục 2 (biểu đồ usecase chức năng giao hàng ở hình 5, biểu đồ activity chức năng giao hàng ở hình 15).</p>
<ul>Đầu vào của thuật toán:</ul>
	<li>Một danh sách tọa độ của các đơn hàng nhận từ bài toán chia đơn.</li>
	<li>Danh sách giá trị thời gian di chuyển giữa các điểm trong danh sách đơn hàng và vị trí của siêu thị.</li>
	<li>Tọa độ vị trí của siêu thị.</li>
	<li>Số lượng của người giao hàng là 1.</li>
	<li>Các thông số thích nghi (được cài đặt mặc định trong thuật toán).</li>
 <img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/4dfe8921-1614-4771-aae5-f5e273db3045"/>
<p>Hình 3 Đầu vào của bài toán lựa chọn đơn hàng tối ưu</p>
<ul>Đầu ra của thuật toán:</ul>
	<li>Một danh sách tọa độ các vị trí của đơn hàng đã được sắp xếp thứ tự.</li>
	<li>Độ phù hợp tối ưu hơn (tổng thời gian vận chuyển của chu trình nhỏ hơn).</li> 
  <img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/59ec2597-b577-471a-bf78-12d340096bab"/>
<p>Hình 4 Đầu ra của bài toán lựa chọn đơn hàng tối ưu</p>
<h2>Môi trường thực nghiệm</h2>
<p class="text">Các thông số: numOfLoops = 20000, c1 = 2, c2 = 2, w = 0.9. Số lượng cá thể thay đổi từ 50 đến 200 cá thể. Số lượng đơn hàng từ 10 đến 200 đơn, số lượng người giao hàng tăng tương ứng từ 2 đến 40.</p>
<h2>Dữ liệu thực nghiệm</h2>
<p class="text">Dữ liệu danh sách thời gian di chuyển giữa các điểm sử dụng 40000 dòng để thực nghiệm (tương ứng 200 vị trí giao hàng)</p>
<h2>Kết quả thực nghiệm</h2>
<img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/b5d6e9d2-ece8-4941-8f75-244517ddbe85">
<img width="472" alt="image" src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/b72ffb54-d293-46c2-929c-27e53926deeb">
<img src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/54c1c674-5c16-4634-a666-9902d30bf757">
<h2>Kết quả biểu diễn trong bản đồ</h2>
<img src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/4aa62418-47ad-43cb-a2b1-d082a30a287a">
<img src="https://github.com/NguyenPhuLoc666666/DeliveryApp/assets/68956095/7a76b454-ace7-4aa1-a6e1-cab2d3ab06b0">
<h2>Kết luận</h2>
<p class="text">Đồ án tập trung nghiên cứu cách sử dụng thuật toán PSO để giải quyết 2 bài toán, bài toán phân chia đơn hàng và bài toán lựa chọn đơn hàng tối ưu để đề xuất đơn hàng tiếp theo cho người giao hàng. Dự liệu được sử dụng là dữ liệu thực tế.</p>
<p>Thêm các ràng buộc vào thuật toán:</p>
<p class="text">Bài toán chia đơn: Tổng độ phù hợp (tổng thời gian vận chuyển) của tất cả người giao hàng là nhỏ nhất trong khả năng của thuật toán. Mỗi đơn hàng chỉ được giao bởi một người giao hàng. Điểm xuất phát và điểm kết thúc của mỗi người giao hàng là vị trí của siêu thị. Số lượng đơn hàng của người giao hàng được chia ngẫu nhiên theo số đơn tối thiểu Pmin và số đơn tối đa Pmax.</p>
<p class="text">Bài toán lựa chọn đơn hàng tối ưu: Tổng độ phù hợp (tổng thời gian vận chuyển) là nhỏ nhất trong khả năng của thuật toán. Người giao hàng phải giao tất cả các đơn có trạng thái cần giao. Điểm xuất phát và điểm kết thúc của người giao hàng là vị trí của siêu thị. Người giao hàng có thể lựa chọn đơn hàng tiếp theo để giao theo đề xuất trong kết quả đầu ra của bài toán lựa chọn đơn hàng tối ưu.
</p>

