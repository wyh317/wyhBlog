<%--保留此处 start--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%--保留此处 end--%>
<rapid:override name="title">
    - 修改文章
</rapid:override>

<rapid:override name="header-style">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/editor.md@1.5.0/css/editormd.min.css">
    <style>
        /* 编辑器包装器 */
        #editormd-wrapper {
            margin: 0;
            width: 100%;
            max-width: 100%;
        }
        /* 编辑器容器 */
        #editormd {
            width: 100%;
            margin: 0;
            border: 1px solid #e6e6e6;
        }
        /* 工具栏样式 */
        .editormd-toolbar {
            background: #f8f8f8 !important;
            border-bottom: 1px solid #e6e6e6 !important;
        }
        .editormd-toolbar li a:hover {
            background: #e6e6e6 !important;
        }
        /* 预览区域样式 */
        .editormd-preview-container {
            background: #fff !important;
            border-left: 1px solid #e6e6e6 !important;
        }
        /* 编辑区域样式 */
        .editormd-editor-container {
            border-right: none !important;
        }
        .editormd-textarea-container textarea {
            font-family: "Courier New", Consolas, "Courier", monospace !important;
            font-size: 14px !important;
            line-height: 1.6 !important;
        }
        /* 对话框 z-index */
        .editormd-dialog, .editormd-dialog-mask {
            z-index: 99999 !important;
        }
        /* 修复 Layui 表单样式冲突 */
        .layui-input-block {
            margin-left: 110px;
        }
        .layui-form-label {
            width: 100px;
        }
    </style>
</rapid:override>

<rapid:override name="content">
    <blockquote class="layui-elem-quote">
        <span class="layui-breadcrumb" lay-separator="/">
              <a href="/admin">首页</a>
              <a href="/admin/article">文章列表</a>
              <a><cite>修改文章</cite></a>
        </span>
    </blockquote>

    <form class="layui-form" method="post" id="myForm" action="/admin/article/editSubmit">
        <input type="hidden" name="articleId" value="${article.articleId}">
        <div class="layui-form-item">
            <label class="layui-form-label">标题 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <input type="text" name="articleTitle" lay-verify="title" id="title" value="${article.articleTitle}"
                       class="layui-input">
            </div>
        </div>

        <!-- Markdown 编辑器 -->
        <div class="layui-form-item">
            <label class="layui-form-label">内容 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <div id="editormd-wrapper">
                    <div id="editormd">
                        <textarea name="articleContent" id="articleContent" placeholder="请输入文章内容，支持 Markdown 语法...">${article.articleContent}</textarea>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">分类 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-inline">
                <select name="articleParentCategoryId" id="articleParentCategoryId"
                        lay-filter="articleParentCategoryId">
                    <option value="">一级分类</option>
                    <c:forEach items="${categoryList}" var="c">
                        <c:if test="${c.categoryPid==0}">
                            <option value="${c.categoryId}" <c:if test="${article.categoryList[0].categoryId == c.categoryId}">selected</c:if>>${c.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="articleChildCategoryId" id="articleChildCategoryId">
                    <option value="">二级分类</option>
                    <c:forEach items="${categoryList}" var="c">
                        <c:if test="${c.categoryPid!=0}">
                            <option value="${c.categoryId}" <c:if test="${article.categoryList[1].categoryId == c.categoryId}">selected</c:if>>${c.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">标签</label>
            <div class="layui-input-block">
                <c:forEach items="${tagList}" var="t">
                    <c:set var="checked" value="false"/>
                    <c:forEach items="${article.tagList}" var="at">
                        <c:if test="${at.tagId == t.tagId}">
                            <c:set var="checked" value="true"/>
                        </c:if>
                    </c:forEach>
                    <input type="checkbox" name="articleTagIds" lay-skin="primary" title="${t.tagName}" value="${t.tagId}" <c:if test="${checked}">checked</c:if>>
                </c:forEach>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="radio" name="articleStatus" value="1" title="发布" <c:if test="${article.articleStatus == 1}">checked</c:if>>
                <input type="radio" name="articleStatus" value="0" title="草稿" <c:if test="${article.articleStatus == 0}">checked</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

        <blockquote class="layui-elem-quote layui-quote-nm">
            温馨提示：<br>
            1、本编辑器支持 <strong>Markdown</strong> 语法，右侧可实时预览效果<br>
            2、插入代码块会自动高亮，支持语法：Java、JavaScript、Python、SQL 等主流语言<br>
            3、修改文章后请点击保存按钮，否则内容不会更新

        </blockquote>
    </form>

</rapid:override>


<rapid:override name="footer-script">
    <script src="https://cdn.jsdelivr.net/npm/editor.md@1.5.0/editormd.min.js"></script>
    <script>
        var editormdEditor;

        layui.use(['form', 'laydate'], function () {
            var form = layui.form
                , layer = layui.layer
                , laydate = layui.laydate;

            // 初始化 Markdown 编辑器
            editormdEditor = editormd("editormd", {
                width: "100%",
                height: 640,
                path: "https://cdn.jsdelivr.net/npm/editor.md@1.5.0/lib/",
                theme: "default",
                previewTheme: "default",
                editorTheme: "default",
                markdown: "",
                codeFold: true,
                saveHTMLToTextarea: true,
                searchReplace: true,
                watch: true,
                htmlDecode: "style,script,iframe|on*",
                toolbar: [
                    "undo", "redo", "|",
                    "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
                    "h1", "h2", "h3", "h4", "h5", "h6", "|",
                    "list-ul", "list-ol", "hr", "|",
                    "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|",
                    "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|",
                    "help", "info"
                ],
                placeholder: "请输入文章内容，支持 Markdown 语法...",
                emoji: true,
                taskList: true,
                tocm: true,
                tex: true,
                flowChart: true,
                sequenceDiagram: true,
                onload: function() {
                    console.log('Editor.md loaded successfully');
                    this.watch();
                }
            });

            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value.length < 5) {
                        return '标题至少得 5 个字符啊';
                    }
                }
                , content: function (value) {
                    if(editormdEditor) {
                        editormdEditor.sync();
                    }
                    var content = $("#articleContent").val();
                    if (content.length < 10) {
                        return '文章内容至少 10 个字符';
                    }
                }
            });

            layui.use('code', function(){
                layui.code();
            });

            //二级联动
            form.on("select(articleParentCategoryId)", function () {
                var str = "";
                var articleParentCategoryId = $("#articleParentCategoryId").val();
                <c:forEach items="${categoryList}" var="c">
                if (articleParentCategoryId ==${c.categoryPid}) {
                    str += "<option name='childCategory' value='${c.categoryId}'<c:if test='${article.categoryList[1].categoryId == c.categoryId}'>selected</c:if>>${c.categoryName}</option>";
                }
                </c:forEach>
                $("#articleChildCategoryId").html("  <option value=''selected>二级分类</option>" + str);
                form.render('select');
            })

            // 表单提交前同步编辑器内容
            layui.form.on('submit(demo1)', function(data) {
                if(editormdEditor) {
                    editormdEditor.sync();
                }
                return true;
            });
        });

        window.onbeforeunload = function() {
            return "确认离开当前页面吗？未保存的数据将会丢失";
        }
    </script>

</rapid:override>


<%--此句必须放在最后--%>
<%@ include file="../Public/framework.jsp"%>
