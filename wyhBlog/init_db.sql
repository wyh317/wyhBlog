-- 创建数据库表结构

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_pass` varchar(255) NOT NULL,
  `user_nickname` varchar(50) DEFAULT NULL,
  `user_email` varchar(100) DEFAULT NULL,
  `user_url` varchar(255) DEFAULT NULL,
  `user_avatar` varchar(255) DEFAULT NULL,
  `user_last_login_ip` varchar(50) DEFAULT NULL,
  `user_register_time` datetime DEFAULT NULL,
  `user_last_login_time` datetime DEFAULT NULL,
  `user_status` int(1) DEFAULT '1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章表
CREATE TABLE IF NOT EXISTS `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_user_id` int(11) NOT NULL,
  `article_title` varchar(200) NOT NULL,
  `article_content` longtext,
  `article_summary` varchar(500) DEFAULT NULL,
  `article_view_count` int(11) DEFAULT '0',
  `article_comment_count` int(11) DEFAULT '0',
  `article_like_count` int(11) DEFAULT '0',
  `article_create_time` datetime DEFAULT NULL,
  `article_update_time` datetime DEFAULT NULL,
  `article_is_comment` int(1) DEFAULT '1',
  `article_status` int(1) DEFAULT '1',
  `article_order` int(11) DEFAULT '0',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 分类表
CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_pid` int(11) DEFAULT '0',
  `category_name` varchar(50) NOT NULL,
  `category_description` varchar(255) DEFAULT NULL,
  `category_order` int(11) DEFAULT '0',
  `category_icon` varchar(50) DEFAULT NULL,
  `category_status` int(1) DEFAULT '1',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章标签关联表
CREATE TABLE IF NOT EXISTS `article_tag_ref` (
  `article_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文章分类关联表
CREATE TABLE IF NOT EXISTS `article_category_ref` (
  `article_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`article_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 标签表
CREATE TABLE IF NOT EXISTS `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(50) NOT NULL,
  `tag_description` varchar(255) DEFAULT NULL,
  `tag_order` int(11) DEFAULT '0',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_pid` int(11) DEFAULT '0',
  `comment_pname` varchar(50) DEFAULT NULL,
  `comment_article_id` int(11) NOT NULL,
  `comment_user_id` int(11) DEFAULT NULL,
  `comment_author_name` varchar(50) DEFAULT NULL,
  `comment_author_email` varchar(100) DEFAULT NULL,
  `comment_author_url` varchar(255) DEFAULT NULL,
  `comment_author_avatar` varchar(255) DEFAULT NULL,
  `comment_content` text,
  `comment_agent` varchar(255) DEFAULT NULL,
  `comment_ip` varchar(50) DEFAULT NULL,
  `comment_create_time` datetime DEFAULT NULL,
  `comment_role` int(1) DEFAULT '0',
  `comment_status` int(1) DEFAULT '1',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 菜单表
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) NOT NULL,
  `menu_url` varchar(255) DEFAULT NULL,
  `menu_level` int(1) DEFAULT '1',
  `menu_icon` varchar(50) DEFAULT NULL,
  `menu_order` int(11) DEFAULT '0',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 友情链接表
CREATE TABLE IF NOT EXISTS `link` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `link_name` varchar(50) NOT NULL,
  `link_url` varchar(255) NOT NULL,
  `link_image` varchar(255) DEFAULT NULL,
  `link_description` varchar(255) DEFAULT NULL,
  `link_owner_nickname` varchar(50) DEFAULT NULL,
  `link_owner_contact` varchar(100) DEFAULT NULL,
  `link_update_time` datetime DEFAULT NULL,
  `link_create_time` datetime DEFAULT NULL,
  `link_order` int(11) DEFAULT '0',
  `link_status` int(1) DEFAULT '1',
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 公告表
CREATE TABLE IF NOT EXISTS `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(200) NOT NULL,
  `notice_content` text,
  `notice_create_time` datetime DEFAULT NULL,
  `notice_update_time` datetime DEFAULT NULL,
  `notice_status` int(1) DEFAULT '1',
  `notice_order` int(11) DEFAULT '0',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 页面表
CREATE TABLE IF NOT EXISTS `page` (
  `page_id` int(11) NOT NULL AUTO_INCREMENT,
  `page_key` varchar(100) DEFAULT NULL,
  `page_title` varchar(200) NOT NULL,
  `page_content` longtext,
  `page_create_time` datetime DEFAULT NULL,
  `page_update_time` datetime DEFAULT NULL,
  `page_view_count` int(11) DEFAULT '0',
  `page_comment_count` int(11) DEFAULT '0',
  `page_status` int(1) DEFAULT '1',
  PRIMARY KEY (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 选项配置表
CREATE TABLE IF NOT EXISTS `options` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT,
  `option_site_title` varchar(100) DEFAULT NULL,
  `option_site_descrption` text,
  `option_meta_descrption` text,
  `option_meta_keyword` text,
  `option_aboutsite_avatar` varchar(255) DEFAULT NULL,
  `option_aboutsite_title` varchar(100) DEFAULT NULL,
  `option_aboutsite_content` text,
  `option_aboutsite_wechat` varchar(50) DEFAULT NULL,
  `option_aboutsite_qq` varchar(50) DEFAULT NULL,
  `option_aboutsite_github` varchar(100) DEFAULT NULL,
  `option_aboutsite_weibo` varchar(100) DEFAULT NULL,
  `option_tongji` text,
  `option_status` int(1) DEFAULT '1',
  PRIMARY KEY (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据

-- 默认管理员用户 (密码：123456)
INSERT INTO `user` (`user_name`, `user_pass`, `user_nickname`, `user_email`, `user_status`, `user_register_time`) 
VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@example.com', 1, NOW());

-- 默认菜单
INSERT INTO `menu` (`menu_name`, `menu_url`, `menu_level`, `menu_order`) VALUES 
('首页', '/', 1, 1),
('文章归档', '/article', 1, 2);

-- 默认分类
INSERT INTO `category` (`category_name`, `category_description`, `category_order`, `category_icon`, `category_status`) VALUES 
('技术', '技术文章', 1, '', 1),
('生活', '生活随笔', 2, '', 1);

-- 默认标签
INSERT INTO `tag` (`tag_name`, `tag_description`, `tag_order`) VALUES 
('Java', 'Java 相关', 1),
('Spring', 'Spring 框架', 2);

-- 默认配置
INSERT INTO `options` (`option_site_title`, `option_site_descrption`, `option_status`) VALUES 
('ForestBlog', '个人博客系统', 1);
