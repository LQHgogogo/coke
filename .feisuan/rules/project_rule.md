
# 开发规范指南
为保证代码质量、可维护性、安全性与可扩展性，请在开发过程中严格遵循以下规范。

## 一、技术栈与工具要求

- **操作系统**：Windows 11
- **工作区路径**：`D:\代码\coke`
- **Java 版本**：
    - Maven 配置中编译源码设为 `14` (source/target)，请确保开发环境 JDK 版本至少为 JDK 14。
    - 项目信息中提及 JDK 1.8，请根据实际运行环境调整或统一版本。
- **构建工具**：Maven
- **核心依赖**：
    - `gson` (2.10.1)：用于 JSON 序列化与反序列化。
- **代码作者**：HQY

## 二、目录结构规范

项目标准的 Maven 目录结构如下（请遵循此结构）：

```text
coke (工作区根目录)
└── src
    └── com
        └── cqut
            ├── domain (领域模型/实体层)
            │   └── impl (实体实现类，如有)
            └── ui (用户界面/控制器层)
                └── impl (UI 实现类，如有)
```

> **注意**：当前项目结构中 `ui` 目录位于 `com.cqut` 下，请统一管理。

## 三、通用开发规则

### 依赖使用规范
- **JSON 处理**：
    - 本项目使用 `com.google.gson.Gson` 库。
    - **禁止**使用 Java 内置的 `org.json` 或 `javax.json` 等其他第三方 JSON 库。
    - 推荐在使用处添加静态导入以保持代码整洁：`import static com.google.gson.GsonBuilder;`

### JSON 序列化与反序列化规范
- **对象转换**：
    - 使用 `Gson` 对象将 POJO/Entity 转换为 JSON 字符串。
    - 使用 `Gson.fromJson()` 将 JSON 字符串解析回 POJO/Entity。
- **时间格式化**：
    - 处理时间类型（如 `java.util.Date` 或 `java.time`）时，务必在 `Gson` 实例化时配置 `JsonSerializer` 或使用注解，统一返回给前端的时间格式（建议为 ISO 8601 或指定格式），避免出现时间戳差异。

## 四、代码风格规范

### 命名规范

| 类型       | 命名方式             | 示例                  |
|------------|----------------------|-----------------------|
| 类名       | UpperCamelCase       | `UserService`         |
| 方法/变量  | lowerCamelCase       | `saveUser()`          |
| 常量       | UPPER_SNAKE_CASE     | `MAX_LOGIN_ATTEMPTS`  |

### 注释规范
- **作者标志**：文件头部必须标注代码作者 `HQY`。
- **语言**：代码注释请使用 **中文**（用户的开发第一语言）。
- **内容**：所有类、方法、重要字段需添加注释，说明其功能、参数及返回值。

### 实体类简化工具
- 使用 Lombok 注解替代手动编写 getter/setter/构造方法：
    - `@Data`
    - `@NoArgsConstructor`
    - `@AllArgsConstructor`

## 五、扩展性与日志规范

### 日志记录
- 虽然 `pom.xml` 中未显式引入 `log4j` 或 `slf4j`，但强烈建议在代码中引入日志框架。
- **推荐**：添加 `slf4j-log4j12` 依赖，并使用 `@Slf4j` 注解进行日志记录，严禁使用 `System.out.println` 或 `System.err.println`。

## 六、编码原则总结

| 原则       | 说明                                       |
|------------|--------------------------------------------|
| **SOLID**  | 高内聚、低耦合，增强可维护性与可扩展性     |
| **DRY**    | 避免重复代码，提高复用性                   |
| **KISS**   | 保持代码简洁易懂                           |
| **YAGNI**  | 不实现当前不需要的功能                     |
| **OWASP**  | 防范常见安全漏洞，如 JSON 注入等           |

## 七、快速导入建议

```java
import static com.google.gson.GsonBuilder.create;
