-- 添加更多分类
INSERT INTO category (category_pid, category_name, category_description, category_order, category_icon, category_status) VALUES 
(0, '前端开发', 'Web 前端技术文章', 3, 'fa fa-code', 1),
(0, '后端技术', 'Java Spring 等后端技术', 4, 'fa fa-server', 1),
(0, '数据库', 'MySQL Redis 等数据库技术', 5, 'fa fa-database', 1),
(0, '运维部署', 'Linux Docker 等运维知识', 6, 'fa fa-cogs', 1),
(0, '读书笔记', '技术书籍读后感', 7, 'fa fa-book', 1),
(0, '生活随笔', '日常生活记录', 8, 'fa fa-coffee', 1);

-- 添加更多标签
INSERT INTO tag (tag_name, tag_description, tag_order) VALUES 
('JavaScript', 'JavaScript 编程语言', 1),
('Vue.js', 'Vue.js 前端框架', 2),
('React', 'React 前端框架', 3),
('Spring Boot', 'Spring Boot 框架', 4),
('MyBatis', 'MyBatis 持久层框架', 5),
('MySQL', 'MySQL 数据库', 6),
('Redis', 'Redis 缓存数据库', 7),
('Linux', 'Linux 操作系统', 8),
('Docker', 'Docker 容器技术', 9),
('Git', 'Git 版本控制', 10),
('算法', '数据结构与算法', 11),
('设计模式', '软件设计模式', 12);

-- 添加文章
INSERT INTO article (article_user_id, article_title, article_summary, article_content, article_view_count, article_comment_count, article_like_count, article_create_time, article_update_time, article_is_comment, article_status, article_order) VALUES 
(1, 'Spring Boot 入门教程', '本文详细介绍 Spring Boot 的基础知识，帮助你快速入门', 'Spring Boot 是由 Pivotal 团队提供的全新框架，其设计目的是用来简化新 Spring 应用的初始搭建以及开发过程。主要优势包括：创建独立的 Spring 应用程序、嵌入的 Tomcat、简化 Maven 配置、自动配置 Spring 等。', 1523, 28, 156, DATE_SUB(NOW(), INTERVAL 30 DAY), NOW(), 1, 1, 1),
(1, 'Vue.js 3.0 新特性解析', 'Vue.js 3.0 带来了哪些新特性？本文带你深入了解', 'Vue.js 3.0 于 2020 年 9 月正式发布，带来了许多令人兴奋的新特性。Composition API 是 Vue 3 最大的亮点，它提供了一种新的代码组织方式。性能提升 1.3-2 倍，更好的 TypeScript 支持。', 2341, 45, 203, DATE_SUB(NOW(), INTERVAL 25 DAY), NOW(), 1, 1, 2),
(2, 'MySQL 索引优化实战', 'MySQL 索引优化的最佳实践，让你的查询飞起来', '索引是数据库优化最重要的手段之一。本文将分享一些 MySQL 索引优化的实战经验。B-Tree 索引是最常用的索引类型，适用于范围查询。Hash 索引仅支持等值查询。', 1876, 32, 178, DATE_SUB(NOW(), INTERVAL 20 DAY), NOW(), 1, 1, 3),
(1, 'Docker 容器化部署指南', '使用 Docker 容器化部署应用，提高部署效率', 'Docker 已经成为现代应用部署的标准工具。镜像包含应用运行环境的只读模板，容器是镜像的运行实例。使用 Dockerfile 可以方便地构建镜像。', 1234, 19, 98, DATE_SUB(NOW(), INTERVAL 15 DAY), NOW(), 1, 1, 4),
(3, 'Redis 缓存设计最佳实践', '如何设计高效的 Redis 缓存系统', 'Redis 作为高性能的键值存储系统，广泛应用于缓存场景。String 是最简单的类型，适合缓存对象。Hash 适合存储对象的部分字段。List 适合消息队列。', 2156, 38, 189, DATE_SUB(NOW(), INTERVAL 10 DAY), NOW(), 1, 1, 5),
(2, 'Git 版本控制最佳实践', 'Git 使用技巧，提高团队协作效率', 'Git 是现代软件开发中不可或缺的工具。分支管理策略包括 main、develop、feature、release、hotfix 等分支。Commit 规范很重要。', 1678, 25, 142, DATE_SUB(NOW(), INTERVAL 8 DAY), NOW(), 1, 1, 6),
(1, 'Java 8 Stream API 详解', 'Java 8 Stream API 使用指南', 'Java 8 引入的 Stream API 极大地简化了集合操作。可以从集合、数组创建 Stream。常用操作包括 filter、map、reduce、collect 等。', 1923, 31, 167, DATE_SUB(NOW(), INTERVAL 5 DAY), NOW(), 1, 1, 7),
(3, 'Linux 常用命令速查表', 'Linux 开发者必备命令清单', '作为开发者，熟练掌握 Linux 命令是必备技能。文件操作包括 cat、less、find、grep 等。进程管理包括 ps、top、kill 等。', 2567, 42, 231, DATE_SUB(NOW(), INTERVAL 3 DAY), NOW(), 1, 1, 8),
(1, '设计模式之单例模式', '单例模式的多种实现方式及优缺点', '单例模式是最简单也是最常用的设计模式之一。饿汉式在类加载时就创建实例。懒汉式使用双重检查锁。静态内部类方式推荐。', 1456, 22, 134, DATE_SUB(NOW(), INTERVAL 2 DAY), NOW(), 1, 1, 9),
(2, '如何成为一名优秀的程序员', '分享一些成为优秀程序员的经验和建议', '编程不仅仅是一份工作，更是一门艺术。持续学习很重要，要关注技术动态，阅读优秀源码。编码习惯也很关键，要编写可读性高的代码。', 3421, 67, 312, DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), 1, 1, 10);

-- 关联文章和分类
INSERT INTO article_category_ref (article_id, category_id) VALUES (1, 2), (1, 4), (2, 3), (3, 5), (4, 6), (5, 5), (6, 7), (7, 2), (8, 6), (9, 2), (10, 8);

-- 关联文章和标签
INSERT INTO article_tag_ref (article_id, tag_id) VALUES (1, 4), (1, 5), (2, 2), (2, 1), (3, 6), (3, 11), (4, 9), (4, 8), (5, 7), (6, 10), (7, 4), (7, 1), (8, 8), (9, 12), (10, 11);

-- 添加评论
INSERT INTO comment (comment_pid, comment_pname, comment_article_id, comment_author_name, comment_author_email, comment_content, comment_agent, comment_ip, comment_create_time, comment_role, comment_status) VALUES 
(0, NULL, 1, '小明', 'xiaoming@example.com', '文章写得很好，学到了很多！', 'Mozilla/5.0', '192.168.1.100', DATE_SUB(NOW(), INTERVAL 29 DAY), 0, 1),
(0, NULL, 1, '小红', 'xiaohong@example.com', 'Spring Boot 确实很方便，推荐大家使用', 'Mozilla/5.0', '192.168.1.101', DATE_SUB(NOW(), INTERVAL 28 DAY), 0, 1),
(0, NULL, 2, '前端小白', 'fe@example.com', 'Vue 3 的 Composition API 真的很好用', 'Mozilla/5.0', '192.168.1.102', DATE_SUB(NOW(), INTERVAL 24 DAY), 0, 1),
(0, NULL, 3, 'DBA 老张', 'dba@example.com', '索引优化确实重要，我们生产环境优化后查询快了 10 倍', 'Mozilla/5.0', '192.168.1.104', DATE_SUB(NOW(), INTERVAL 19 DAY), 0, 1),
(0, NULL, 4, '运维小哥', 'ops@example.com', 'Docker 部署真的很方便，强烈推荐使用', 'Mozilla/5.0', '192.168.1.105', DATE_SUB(NOW(), INTERVAL 14 DAY), 0, 1),
(0, NULL, 5, '缓存专家', 'cache@example.com', 'Redis 的过期策略设计很有讲究', 'Mozilla/5.0', '192.168.1.106', DATE_SUB(NOW(), INTERVAL 9 DAY), 0, 1),
(0, NULL, 6, 'Git 达人', 'git@example.com', 'commit 规范很重要，我们团队一直在用', 'Mozilla/5.0', '192.168.1.107', DATE_SUB(NOW(), INTERVAL 7 DAY), 0, 1),
(0, NULL, 7, 'Java 老兵', 'java@example.com', 'Stream API 让代码简洁了很多', 'Mozilla/5.0', '192.168.1.108', DATE_SUB(NOW(), INTERVAL 4 DAY), 0, 1),
(0, NULL, 8, 'Linux 爱好者', 'linux@example.com', '已收藏，经常查阅', 'Mozilla/5.0', '192.168.1.109', DATE_SUB(NOW(), INTERVAL 2 DAY), 0, 1),
(0, NULL, 9, '设计模式学习者', 'dp@example.com', '静态内部类的方式确实很优雅', 'Mozilla/5.0', '192.168.1.110', DATE_SUB(NOW(), INTERVAL 1 DAY), 0, 1),
(0, NULL, 10, '职场新人', 'newbie@example.com', '感谢分享，受益匪浅！', 'Mozilla/5.0', '192.168.1.111', NOW(), 0, 1);

-- 添加友情链接
INSERT INTO link (link_name, link_url, link_description, link_order, link_status) VALUES 
('阮一峰的网络日志', 'https://www.ruanyifeng.com/blog/', '技术大牛的博客，内容涵盖前端 Linux Git 等', 1, 1),
('廖雪峰的官方网站', 'https://www.liaoxuefeng.com/', 'Python Git Java 等教程', 2, 1),
('美团技术团队', 'https://tech.meituan.com/', '美团技术团队博客', 3, 1),
('掘金', 'https://juejin.cn/', '一个帮助开发者成长的社区', 4, 1);

-- 添加公告
INSERT INTO notice (notice_title, notice_content, notice_create_time, notice_update_time, notice_status, notice_order) VALUES 
('欢迎来到 ForestBlog', '感谢访问 ForestBlog 个人博客系统！这里会分享技术文章、学习笔记和生活感悟。', NOW(), NOW(), 1, 1),
('博客更新计划', '2024 年计划更新：Spring Boot 系列教程、Vue.js 实战、算法刷题笔记等。', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW(), 1, 2);

-- 添加页面
INSERT INTO page (page_key, page_title, page_content, page_create_time, page_update_time, page_view_count, page_comment_count, page_status) VALUES 
('about', '关于我', '你好，我是一名热爱技术的程序员。技术栈包括：后端 Java Spring Boot，前端 Vue.js React，数据库 MySQL Redis。', DATE_SUB(NOW(), INTERVAL 365 DAY), NOW(), 523, 8, 1),
('links', '友情链接', '以下是一些我推荐的技术博客和资源。', DATE_SUB(NOW(), INTERVAL 300 DAY), NOW(), 312, 3, 1);

-- 更新配置
UPDATE options SET option_site_title='ForestBlog - 个人博客系统', option_site_descrption='一个基于 Spring Boot 的个人博客系统，分享技术文章和学习笔记', option_meta_descrption='ForestBlog 个人博客，分享 Java、Spring Boot、Vue.js 等技术文章', option_meta_keyword='博客，个人博客，Spring Boot,Java,Vue.js，技术博客' WHERE option_id=1;

-- 更新菜单
DELETE FROM menu;
INSERT INTO menu (menu_name, menu_url, menu_level, menu_icon, menu_order) VALUES 
('首页', '/', 1, 'fa fa-home', 1),
('文章归档', '/article', 1, 'fa fa-archive', 2),
('分类目录', NULL, 1, 'fa fa-folder', 3),
('标签云', NULL, 1, 'fa fa-tags', 4),
('关于', '/page/about', 1, 'fa fa-user', 5),
('友情链接', '/page/links', 1, 'fa fa-link', 6);
