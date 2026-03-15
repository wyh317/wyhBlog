-- 插入模拟博客数据

-- 1. 添加更多用户
INSERT INTO `user` (`user_name`, `user_pass`, `user_nickname`, `user_email`, `user_status`, `user_register_time`) 
VALUES 
('zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'zhangsan@example.com', 1, DATE_SUB(NOW(), INTERVAL 365 DAY)),
('lisi', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'lisi@example.com', 1, DATE_SUB(NOW(), INTERVAL 300 DAY)),
('wangwu', 'e10adc3949ba59abbe56e057f20f883e', '王五', 'wangwu@example.com', 1, DATE_SUB(NOW(), INTERVAL 200 DAY));

-- 2. 添加更多分类
INSERT INTO `category` (`category_pid`, `category_name`, `category_description`, `category_order`, `category_icon`, `category_status`) VALUES 
(0, '前端开发', 'Web 前端技术文章', 3, 'fa fa-code', 1),
(0, '后端技术', 'Java、Spring 等后端技术', 4, 'fa fa-server', 1),
(0, '数据库', 'MySQL、Redis 等数据库技术', 5, 'fa fa-database', 1),
(0, '运维部署', 'Linux、Docker 等运维知识', 6, 'fa fa-cogs', 1),
(0, '读书笔记', '技术书籍读后感', 7, 'fa fa-book', 1),
(0, '生活随笔', '日常生活记录', 8, 'fa fa-coffee', 1);

-- 3. 添加更多标签
INSERT INTO `tag` (`tag_name`, `tag_description`, `tag_order`) VALUES 
('JavaScript', 'JavaScript 编程语言'),
('Vue.js', 'Vue.js 前端框架'),
('React', 'React 前端框架'),
('Spring Boot', 'Spring Boot 框架'),
('MyBatis', 'MyBatis 持久层框架'),
('MySQL', 'MySQL 数据库'),
('Redis', 'Redis 缓存数据库'),
('Linux', 'Linux 操作系统'),
('Docker', 'Docker 容器技术'),
('Git', 'Git 版本控制'),
('算法', '数据结构与算法'),
('设计模式', '软件设计模式');

-- 4. 添加文章
INSERT INTO `article` (`article_user_id`, `article_title`, `article_summary`, `article_content`, `article_view_count`, `article_comment_count`, `article_like_count`, `article_create_time`, `article_update_time`, `article_is_comment`, `article_status`, `article_order`) VALUES 
(1, 'Spring Boot 入门教程', '本文详细介绍 Spring Boot 的基础知识，帮助你快速入门', 
'<h1>Spring Boot 入门教程</h1><p>Spring Boot 是由 Pivotal 团队提供的全新框架，其设计目的是用来简化新 Spring 应用的初始搭建以及开发过程。</p><h2>一、Spring Boot 的优势</h2><ul><li>创建独立的 Spring 应用程序</li><li>嵌入的 Tomcat，无需部署 WAR 文件</li><li>简化 Maven 配置</li><li>自动配置 Spring</li><li>提供生产级别的监控、健康检查和外部化配置</li></ul><h2>二、创建第一个 Spring Boot 项目</h2><p>首先，我们需要在 pom.xml 中添加 Spring Boot 的依赖：</p><pre><code>&lt;parent&gt;&lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;&lt;artifactId&gt;spring-boot-starter-parent&lt;/artifactId&gt;&lt;version&gt;2.7.0&lt;/version&gt;&lt;/parent&gt;</code></pre><h2>三、编写 Hello World</h2><pre><code>@RestController\npublic class HelloController {\n    @RequestMapping("/hello")\n    public String hello() {\n        return "Hello, Spring Boot!";\n    }\n}</code></pre><p>启动应用后，访问 http://localhost:8080/hello 即可看到输出。</p>', 
1523, 28, 156, DATE_SUB(NOW(), INTERVAL 30 DAY), NOW(), 1, 1, 1),

(1, 'Vue.js 3.0 新特性解析', 'Vue.js 3.0 带来了哪些新特性？本文带你深入了解', 
'<h1>Vue.js 3.0 新特性解析</h1>
<p>Vue.js 3.0 于 2020 年 9 月正式发布，带来了许多令人兴奋的新特性。</p>
<h2>一、Composition API</h2>
<p>Composition API 是 Vue 3 最大的亮点，它提供了一种新的代码组织方式：</p>
<pre><code>&lt;script&gt;
import { ref, computed, onMounted } from "vue";

export default {
  setup() {
    const count = ref(0);
    const double = computed(() => count.value * 2);
    
    onMounted(() => {
      console.log("组件已挂载");
    });
    
    return { count, double };
  }
};
&lt;/script&gt;</code></pre>
<h2>二、性能提升</h2>
<ul>
<li>虚拟 DOM 重写，性能提升 1.3-2 倍</li>
<li>Tree-shaking 支持，更小的打包体积</li>
<li>更高效的响应式系统</li>
</ul>
<h2>三、TypeScript 支持</h2>
<p>Vue 3 使用 TypeScript 重写，提供了更好的类型推导和类型检查。</p>', 
2341, 45, 203, DATE_SUB(NOW(), INTERVAL 25 DAY), NOW(), 1, 1, 2),

(2, 'MySQL 索引优化实战', 'MySQL 索引优化的最佳实践，让你的查询飞起来', 
'<h1>MySQL 索引优化实战</h1>
<p>索引是数据库优化最重要的手段之一。本文将分享一些 MySQL 索引优化的实战经验。</p>
<h2>一、索引的类型</h2>
<ul>
<li><strong>B-Tree 索引</strong>：最常用的索引类型，适用于范围查询</li>
<li><strong>Hash 索引</strong>：仅支持等值查询，不支持范围查询</li>
<li><strong>全文索引</strong>：用于全文搜索</li>
<li><strong>空间索引</strong>：用于地理空间数据</li>
</ul>
<h2>二、创建索引的原则</h2>
<ol>
<li>为经常出现在 WHERE 子句中的列创建索引</li>
<li>为经常用于 JOIN 的列创建索引</li>
<li>为经常需要排序的列创建索引</li>
<li>避免为重复值较多的列创建索引</li>
</ol>
<h2>三、索引优化案例</h2>
<pre><code>-- 优化前：全表扫描
SELECT * FROM users WHERE email = "test@example.com";

-- 优化后：使用索引
CREATE INDEX idx_email ON users(email);
SELECT * FROM users WHERE email = "test@example.com";</code></pre>', 
1876, 32, 178, DATE_SUB(NOW(), INTERVAL 20 DAY), NOW(), 1, 1, 3),

(1, 'Docker 容器化部署指南', '使用 Docker 容器化部署应用，提高部署效率', 
'<h1>Docker 容器化部署指南</h1>
<p>Docker 已经成为现代应用部署的标准工具。本文将介绍如何使用 Docker 容器化部署应用。</p>
<h2>一、Docker 基础概念</h2>
<ul>
<li><strong>镜像（Image）</strong>：包含应用运行环境的只读模板</li>
<li><strong>容器（Container）</strong>：镜像的运行实例</li>
<li><strong>Dockerfile</strong>：构建镜像的脚本</li>
</ul>
<h2>二、编写 Dockerfile</h2>
<pre><code>FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/myapp.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]</code></pre>
<h2>三、构建和运行</h2>
<pre><code># 构建镜像
docker build -t myapp:1.0 .

# 运行容器
docker run -d -p 8080:8080 --name myapp myapp:1.0</code></pre>', 
1234, 19, 98, DATE_SUB(NOW(), INTERVAL 15 DAY), NOW(), 1, 1, 4),

(3, 'Redis 缓存设计最佳实践', '如何设计高效的 Redis 缓存系统', 
'<h1>Redis 缓存设计最佳实践</h1>
<p>Redis 作为高性能的键值存储系统，广泛应用于缓存场景。本文将分享 Redis 缓存设计的最佳实践。</p>
<h2>一、数据类型选择</h2>
<ul>
<li><strong>String</strong>：最简单的类型，适合缓存对象</li>
<li><strong>Hash</strong>：适合存储对象的部分字段</li>
<li><strong>List</strong>：适合消息队列、最新列表</li>
<li><strong>Set</strong>：适合去重、共同好友</li>
<li><strong>ZSet</strong>：适合排行榜、优先级队列</li>
</ul>
<h2>二、缓存策略</h2>
<h3>1. Cache Aside Pattern</h3>
<pre><code>// 读操作
value = redis.get(key);
if (value == null) {
    value = db.query(sql);
    redis.set(key, value);
}

// 写操作
db.update(sql);
redis.delete(key);</code></pre>
<h2>三、缓存过期时间</h2>
<p>设置合理的过期时间，避免缓存雪崩：</p>
<pre><code>// 添加随机因子
expireTime = baseTime + random(1, 300);</code></pre>', 
2156, 38, 189, DATE_SUB(NOW(), INTERVAL 10 DAY), NOW(), 1, 1, 5),

(2, 'Git 版本控制最佳实践', 'Git 使用技巧，提高团队协作效率', 
'<h1>Git 版本控制最佳实践</h1>
<p>Git 是现代软件开发中不可或缺的工具。本文将分享一些 Git 使用的最佳实践。</p>
<h2>一、分支管理策略</h2>
<ul>
<li><strong>main/master</strong>：主分支，存放稳定版本</li>
<li><strong>develop</strong>：开发分支，日常开发在此分支</li>
<li><strong>feature/*</strong>：功能分支，开发新功能</li>
<li><strong>release/*</strong>：发布分支，准备新版本</li>
<li><strong>hotfix/*</strong>：热修复分支，紧急修复 bug</li>
</ul>
<h2>二、Commit 规范</h2>
<pre><code>feat: 新功能
fix: 修复 bug
docs: 文档更新
style: 代码格式调整
refactor: 代码重构
test: 测试相关
chore: 构建工具或依赖的变更</code></pre>
<h2>三、常用命令</h2>
<pre><code># 查看提交历史
git log --oneline --graph

# 撤销提交
git reset --soft HEAD~1

# 暂存修改
git stash

# 合并分支
git merge --no-ff feature/login</code></pre>', 
1678, 25, 142, DATE_SUB(NOW(), INTERVAL 8 DAY), NOW(), 1, 1, 6),

(1, 'Java 8 Stream API 详解', 'Java 8 Stream API 使用指南', 
'<h1>Java 8 Stream API 详解</h1>
<p>Java 8 引入的 Stream API 极大地简化了集合操作。本文将详细介绍 Stream API 的使用。</p>
<h2>一、Stream 的创建</h2>
<pre><code>// 从集合创建
List&lt;String&gt; list = Arrays.asList("a", "b", "c");
Stream&lt;String&gt; stream = list.stream();

// 从数组创建
Stream&lt;String&gt; stream = Stream.of("a", "b", "c");

// 生成无限流
Stream&lt;Double&gt; randoms = Stream.generate(Math::random);</code></pre>
<h2>二、常用操作</h2>
<pre><code>// filter - 过滤
list.stream().filter(s -> s.startsWith("a"));

// map - 转换
list.stream().map(String::toUpperCase);

// reduce - 归约
list.stream().reduce("", (a, b) -> a + b);

// collect - 收集
List&lt;String&gt; result = list.stream().collect(Collectors.toList());</code></pre>
<h2>三、并行流</h2>
<pre><code>// 使用并行流提高性能
list.parallelStream()
    .filter(s -> s.length() > 3)
    .collect(Collectors.toList());</code></pre>', 
1923, 31, 167, DATE_SUB(NOW(), INTERVAL 5 DAY), NOW(), 1, 1, 7),

(3, 'Linux 常用命令速查表', 'Linux 开发者必备命令清单', 
'<h1>Linux 常用命令速查表</h1>
<p>作为开发者，熟练掌握 Linux 命令是必备技能。本文整理了一些常用的 Linux 命令。</p>
<h2>一、文件操作</h2>
<pre><code># 查看文件内容
cat file.txt
less file.txt
tail -f log.txt  # 实时查看日志

# 查找文件
find /path -name "*.txt"
grep -r "keyword" /path

# 文件权限
chmod 755 script.sh
chown user:group file.txt</code></pre>
<h2>二、进程管理</h2>
<pre><code># 查看进程
ps aux | grep java
top
htop

# 终止进程
kill PID
kill -9 PID  # 强制终止</code></pre>
<h2>三、网络相关</h2>
<pre><code># 查看端口
netstat -tlnp
ss -tlnp

# 网络测试
ping host
curl -I http://example.com
telnet host port</code></pre>', 
2567, 42, 231, DATE_SUB(NOW(), INTERVAL 3 DAY), NOW(), 1, 1, 8),

(1, '设计模式之单例模式', '单例模式的多种实现方式及优缺点', 
'<h1>设计模式之单例模式</h1>
<p>单例模式是最简单也是最常用的设计模式之一。本文将介绍单例模式的多种实现方式。</p>
<h2>一、饿汉式</h2>
<pre><code>public class Singleton {
    private static final Singleton instance = new Singleton();
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        return instance;
    }
}</code></pre>
<p>优点：线程安全，实现简单</p>
<p>缺点：类加载时就创建实例，可能浪费资源</p>
<h2>二、懒汉式（双重检查锁）</h2>
<pre><code>public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}</code></pre>
<h2>三、静态内部类</h2>
<pre><code>public class Singleton {
    private Singleton() {}
    
    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}</code></pre>
<p>推荐方式：线程安全，延迟加载</p>', 
1456, 22, 134, DATE_SUB(NOW(), INTERVAL 2 DAY), NOW(), 1, 1, 9),

(2, '如何成为一名优秀的程序员', '分享一些成为优秀程序员的经验和建议', 
'<h1>如何成为一名优秀的程序员</h1>
<p>编程不仅仅是一份工作，更是一门艺术。本文将分享一些成为优秀程序员的经验。</p>
<h2>一、持续学习</h2>
<ul>
<li>关注技术动态，阅读技术博客</li>
<li>学习新技术，但不要盲目追新</li>
<li>深入理解基础，算法、数据结构、设计模式</li>
<li>阅读优秀开源项目的源码</li>
</ul>
<h2>二、编码习惯</h2>
<ul>
<li>编写可读性高的代码</li>
<li>遵循代码规范</li>
<li>写单元测试</li>
<li>及时重构，保持代码整洁</li>
<li>写好注释和文档</li>
</ul>
<h2>三、软技能</h2>
<ul>
<li>良好的沟通能力</li>
<li>团队协作精神</li>
<li>时间管理能力</li>
<li>问题分析与解决能力</li>
</ul>
<h2>四、保持热情</h2>
<p>编程是一件需要热情的事情。保持好奇心，享受解决问题的过程，这是持续进步的动力。</p>', 
3421, 67, 312, DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), 1, 1, 10);

-- 5. 关联文章和分类
INSERT INTO `article_category_ref` (`article_id`, `category_id`) VALUES 
(1, 2), (1, 4),
(2, 3),
(3, 5),
(4, 6),
(5, 5),
(6, 7),
(7, 2),
(8, 6),
(9, 2),
(10, 8);

-- 6. 关联文章和标签
INSERT INTO `article_tag_ref` (`article_id`, `tag_id`) VALUES 
(1, 4), (1, 5),
(2, 2), (2, 1),
(3, 6), (3, 11),
(4, 9), (4, 8),
(5, 7),
(6, 10),
(7, 4), (7, 1),
(8, 8),
(9, 12),
(10, 11);

-- 7. 添加评论
INSERT INTO `comment` (`comment_pid`, `comment_pname`, `comment_article_id`, `comment_user_id`, `comment_author_name`, `comment_author_email`, `comment_author_url`, `comment_content`, `comment_agent`, `comment_ip`, `comment_create_time`, `comment_role`, `comment_status`) VALUES 
(0, NULL, 1, NULL, '小明', 'xiaoming@example.com', 'http://xiaoming.com', '文章写得很好，学到了很多！', 'Mozilla/5.0', '192.168.1.100', DATE_SUB(NOW(), INTERVAL 29 DAY), 0, 1),
(0, NULL, 1, NULL, '小红', 'xiaohong@example.com', NULL, 'Spring Boot 确实很方便，推荐大家使用', 'Mozilla/5.0', '192.168.1.101', DATE_SUB(NOW(), INTERVAL 28 DAY), 0, 1),
(1, '小明', 1, 1, 'admin', 'admin@example.com', NULL, '感谢支持！', 'Mozilla/5.0', '127.0.0.1', DATE_SUB(NOW(), INTERVAL 27 DAY), 1, 1),
(0, NULL, 2, NULL, '前端小白', 'fe@example.com', NULL, 'Vue 3 的 Composition API 真的很好用', 'Mozilla/5.0', '192.168.1.102', DATE_SUB(NOW(), INTERVAL 24 DAY), 0, 1),
(0, NULL, 2, NULL, 'React 粉丝', 'react@example.com', NULL, '不过我还是更喜欢 React Hooks', 'Mozilla/5.0', '192.168.1.103', DATE_SUB(NOW(), INTERVAL 23 DAY), 0, 1),
(0, NULL, 3, NULL, 'DBA 老张', 'dba@example.com', NULL, '索引优化确实重要，我们生产环境优化后查询快了 10 倍', 'Mozilla/5.0', '192.168.1.104', DATE_SUB(NOW(), INTERVAL 19 DAY), 0, 1),
(0, NULL, 4, NULL, '运维小哥', 'ops@example.com', NULL, 'Docker 部署真的很方便，强烈推荐使用', 'Mozilla/5.0', '192.168.1.105', DATE_SUB(NOW(), INTERVAL 14 DAY), 0, 1),
(0, NULL, 5, NULL, '缓存专家', 'cache@example.com', NULL, 'Redis 的过期策略设计很有讲究，这篇文章讲得很清楚', 'Mozilla/5.0', '192.168.1.106', DATE_SUB(NOW(), INTERVAL 9 DAY), 0, 1),
(0, NULL, 6, NULL, 'Git 达人', 'git@example.com', NULL, 'commit 规范很重要，我们团队一直在用', 'Mozilla/5.0', '192.168.1.107', DATE_SUB(NOW(), INTERVAL 7 DAY), 0, 1),
(0, NULL, 7, NULL, 'Java 老兵', 'java@example.com', NULL, 'Stream API 让代码简洁了很多', 'Mozilla/5.0', '192.168.1.108', DATE_SUB(NOW(), INTERVAL 4 DAY), 0, 1),
(0, NULL, 8, NULL, 'Linux 爱好者', 'linux@example.com', NULL, '已收藏，经常查阅', 'Mozilla/5.0', '192.168.1.109', DATE_SUB(NOW(), INTERVAL 2 DAY), 0, 1),
(0, NULL, 9, NULL, '设计模式学习者', 'dp@example.com', NULL, '静态内部类的方式确实很优雅', 'Mozilla/5.0', '192.168.1.110', DATE_SUB(NOW(), INTERVAL 1 DAY), 0, 1),
(0, NULL, 10, NULL, '职场新人', 'newbie@example.com', NULL, '感谢分享，受益匪浅！', 'Mozilla/5.0', '192.168.1.111', NOW(), 0, 1);

-- 8. 添加友情链接
INSERT INTO `link` (`link_name`, `link_url`, `link_image`, `link_description`, `link_owner_nickname`, `link_owner_contact`, `link_update_time`, `link_create_time`, `link_order`, `link_status`) VALUES 
('阮一峰的网络日志', 'https://www.ruanyifeng.com/blog/', NULL, '技术大牛的博客，内容涵盖前端、Linux、Git 等', '阮一峰', 'email@example.com', NOW(), DATE_SUB(NOW(), INTERVAL 365 DAY), 1, 1),
('廖雪峰的官方网站', 'https://www.liaoxuefeng.com/', NULL, 'Python、Git、Java 等教程', '廖雪峰', 'email@example.com', NOW(), DATE_SUB(NOW(), INTERVAL 365 DAY), 2, 1),
('美团技术团队', 'https://tech.meituan.com/', NULL, '美团技术团队博客', '美团技术团队', 'email@example.com', NOW(), DATE_SUB(NOW(), INTERVAL 365 DAY), 3, 1),
('阿里云开发者社区', 'https://developer.aliyun.com/', NULL, '阿里云开发者社区', '阿里云', 'email@example.com', NOW(), DATE_SUB(NOW(), INTERVAL 365 DAY), 4, 1),
('掘金', 'https://juejin.cn/', NULL, '一个帮助开发者成长的社区', '掘金', 'email@example.com', NOW(), DATE_SUB(NOW(), INTERVAL 365 DAY), 5, 1);

-- 9. 添加公告
INSERT INTO `notice` (`notice_title`, `notice_content`, `notice_create_time`, `notice_update_time`, `notice_status`, `notice_order`) VALUES 
('欢迎来到 ForestBlog', '<p>感谢访问 ForestBlog 个人博客系统！</p><p>这里会分享技术文章、学习笔记和生活感悟。</p><p>如果你喜欢这里的内容，欢迎收藏本站。</p>', NOW(), NOW(), 1, 1),
('博客更新计划', '<p>2024 年计划更新以下内容：</p><ul><li>Spring Boot 系列教程</li><li>Vue.js 实战</li><li>算法刷题笔记</li><li>读书笔记</li></ul>', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW(), 1, 2);

-- 10. 添加页面
INSERT INTO `page` (`page_key`, `page_title`, `page_content`, `page_create_time`, `page_update_time`, `page_view_count`, `page_comment_count`, `page_status`) VALUES 
('about', '关于我', '<h1>关于我</h1><p>你好，我是一名热爱技术的程序员。</p><h2>技术栈</h2><ul><li>后端：Java, Spring Boot, MyBatis</li><li>前端：Vue.js, React</li><li>数据库：MySQL, Redis</li><li>工具：Git, Docker, Linux</li></ul><h2>联系方式</h2><ul><li>邮箱：contact@example.com</li><li>GitHub: github.com/username</li></ul>', DATE_SUB(NOW(), INTERVAL 365 DAY), NOW(), 523, 8, 1),
('links', '友情链接', '<h1>友情链接</h1><p>以下是一些我推荐的技术博客和资源：</p><ul><li>阮一峰的网络日志</li><li>廖雪峰的官方网站</li><li>美团技术团队</li></ul>', DATE_SUB(NOW(), INTERVAL 300 DAY), NOW(), 312, 3, 1);

-- 11. 更新配置表
INSERT INTO `options` (`option_site_title`, `option_site_descrption`, `option_meta_descrption`, `option_meta_keyword`, `option_aboutsite_avatar`, `option_aboutsite_title`, `option_aboutsite_content`, `option_aboutsite_wechat`, `option_aboutsite_qq`, `option_aboutsite_github`, `option_aboutsite_weibo`, `option_tongji`, `option_status`) VALUES 
('ForestBlog - 个人博客系统', '一个基于 Spring Boot 的个人博客系统，分享技术文章和学习笔记', 'ForestBlog 个人博客，分享 Java、Spring Boot、Vue.js 等技术文章', '博客，个人博客，Spring Boot,Java,Vue.js，技术博客', '/img/avatar.jpg', 'ForestBlog', '欢迎来到 ForestBlog！这里会定期分享技术文章、学习笔记和编程心得。涵盖 Java 后端、前端开发、数据库、运维等多个领域。', 'wechat123', '123456789', 'github123', 'weibo123', '<script>console.log("统计代码");</script>', 1);

-- 12. 添加菜单
DELETE FROM `menu`;
INSERT INTO `menu` (`menu_name`, `menu_url`, `menu_level`, `menu_icon`, `menu_order`) VALUES 
('首页', '/', 1, 'fa fa-home', 1),
('文章归档', '/article', 1, 'fa fa-archive', 2),
('分类目录', NULL, 1, 'fa fa-folder', 3),
('标签云', NULL, 1, 'fa fa-tags', 4),
('关于', '/page/about', 1, 'fa fa-user', 5),
('友情链接', '/page/links', 1, 'fa fa-link', 6);
