# Lesson 4–5–6 (Spring MVC / Tomcat 10)

Ứng dụng gộp các phần thực hành chính trong tài liệu: gallery upload ảnh (Bài 4), CRUD khách hàng và bài hát với Hibernate/JPA (Bài 5), trang NASA “Bức ảnh của ngày”, và CRUD bài viết blog (Bài 6). Source được chia theo `config`, `controller`, `model`, `repository`, `service`; view Thymeleaf nằm riêng trong `src/main/webapp/WEB-INF/views`.

## Công nghệ

- Java 17, Spring Framework 6.2, Jakarta Servlet 6, Thymeleaf
- Hibernate ORM 6, MySQL, Gradle WAR
- Tomcat 10.1 (Servlet 6.0); API Servlet/JPA dùng namespace `jakarta.*`

## Chuẩn bị database và upload

1. Project mặc định dùng H2 dạng file để chạy được ngay; dữ liệu nằm trong `${user.home}/lesson4-5-6-db`.
2. Để dùng MySQL theo bài học, tạo schema bằng `mysql -u root -p < sql/schema.sql`, rồi đổi `db.driver` thành `com.mysql.cj.jdbc.Driver`, `db.url` thành URL MySQL, và điền `db.username`/`db.password`.
3. `upload.directory` mặc định là `${user.home}/lesson4-5-6-uploads/`; ứng dụng tự tạo thư mục. Có thể đổi thành đường dẫn tuyệt đối có quyền ghi.
4. Mở project bằng IntelliJ IDEA, chọn Gradle JVM Java 17, refresh Gradle.

## Deploy lên Tomcat 10 trong IntelliJ IDEA

1. **Settings → Build, Execution, Deployment → Application Servers → + → Tomcat Server**, trỏ tới thư mục cài Tomcat 10.1.
2. **Run → Edit Configurations → + → Tomcat Server → Local**; chọn server vừa khai báo.
3. Tab **Deployment → + → Artifact → `lesson4-5-6:war exploded`** (hoặc deploy `build/libs/lesson4-5-6.war`). Đặt Application context là `/lesson4-5-6`.
4. Tab **Server**: HTTP port `8080`; chọn **Build → Build Artifacts** hoặc để IntelliJ build trước khi chạy.
5. Chạy cấu hình Tomcat, mở `http://localhost:8080/lesson4-5-6/`.

Để tạo artifact exploded, IntelliJ có thể import facet Web từ cấu hình Gradle hoặc dùng **File → Project Structure → Artifacts → + → Web Application: Exploded → From Modules** và chọn module `lesson4-5-6.main`.

## URLs

- `/products` — danh sách sản phẩm và ảnh; `/products/create` — upload ảnh (JPG/PNG/GIF/WebP, tối đa 50 MB).
- `/customers` — tạo, sửa, xóa khách hàng.
- `/blogs` — danh sách, đọc, tạo, sửa, xóa bài viết.
- `/songs` — thêm, nghe, sửa và xóa bài hát; file audio lưu trong `${user.home}/lesson4-5-6-uploads/music/`.
- `/apod` — hiển thị NASA Astronomy Picture of the Day; thay `nasa.apod.api-key` trong `application.properties` bằng API key cá nhân để tránh giới hạn của `DEMO_KEY`.

Gradle tạo WAR bằng `gradle war`. Kết quả: `build/libs/lesson4-5-6.war`.
