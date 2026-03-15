# Markdown 编辑器集成与渲染支持

## 📝 变更概述

本项目添加了完整的 Markdown 编辑器支持和文章详情页的 Markdown 渲染功能，使博客支持现代化的 Markdown 写作体验。

## ✨ 新增功能

### 1. 后台文章编辑器 Markdown 支持
- **新增文件编辑器**：在写文章和编辑文章页面集成 Editor.md 编辑器
- **实时预览**：左侧编辑 Markdown，右侧实时显示预览效果
- **丰富工具栏**：支持粗体、斜体、标题、列表、代码块、表格、链接、图片等
- **代码高亮**：支持多种编程语言的语法高亮
- **表单验证**：添加文章内容长度验证

### 2. 文章详情页 Markdown 渲染
- **Markdown 渲染**：使用 marked.js 在客户端渲染 Markdown 内容
- **GitHub 风格样式**：使用 github-markdown-css 提供美观的文章样式
- **代码高亮**：保留代码块的语法高亮显示
- **响应式布局**：适配不同屏幕尺寸

### 3. 其他修复
- **修复登录页错误**：添加 cookies 空值检查，避免 NullPointerException
- **修复文章查询错误**：更新 getLastUpdateArticle SQL 避免返回多条记录
- **添加 .gitignore**：排除编译产物和 IDE 文件

## 🔧 技术实现

### 使用的库
- **Editor.md** (v1.5.0)：Markdown 编辑器
- **marked.js**：Markdown 渲染引擎
- **github-markdown-css**：GitHub 风格 Markdown 样式

### 修改的文件
1. `wyhBlog/src/main/resources/mapper/ArticleMapper.xml` - 修复 SQL 查询
2. `wyhBlog/src/main/webapp/WEB-INF/view/Admin/login.jsp` - 修复空指针异常
3. `wyhBlog/src/main/webapp/WEB-INF/view/Admin/Article/insert.jsp` - 添加 Markdown 编辑器
4. `wyhBlog/src/main/webapp/WEB-INF/view/Admin/Article/edit.jsp` - 添加 Markdown 编辑器
5. `wyhBlog/src/main/webapp/WEB-INF/view/Home/Page/articleDetail.jsp` - 添加 Markdown 渲染
6. `.gitignore` - 新增文件

## 📸 功能演示

### 后台编辑器
- 访问 `/admin/article/insert` 体验 Markdown 编辑器
- 支持实时预览、代码高亮、表格插入等功能

### 文章详情页
- 访问任意文章页面查看 Markdown 渲染效果
- 支持标题、代码块、引用、表格、列表等 Markdown 元素

## ✅ 测试建议

1. 在后台创建新文章，使用 Markdown 语法编写内容
2. 发布文章后查看前台显示效果
3. 测试代码块、表格、引用等特殊格式的显示
4. 测试编辑已有文章并更新内容

## 📋 Commit 历史

```
0760896 feat: add Markdown rendering for article detail page
88f2c1b feat: add Markdown editor support for edit article page
8dd0413 feat: add Markdown editor support for new article page
5e99afa fix: add null check for cookies in login.jsp
9736c59 fix: update getLastUpdateArticle SQL to avoid returning multiple results
79b44d8 chore: add .gitignore file to exclude build artifacts and IDE files
```

## 🚀 部署说明

1. 合并 PR 后，重新编译项目：`mvn clean package`
2. 部署 WAR 包到 Tomcat
3. 重启 Tomcat 服务
4. 访问后台即可使用 Markdown 编辑器

## 📝 注意事项

- 编辑器使用 CDN 资源，请确保服务器能访问外网
- 如需离线使用，可下载相关库到本地
- 现有 HTML 格式文章仍可正常显示
