<%@ page language="java"
         import="org.apache.commons.fileupload.FileItem,org.apache.commons.fileupload.FileItemFactory,org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.fileupload.servlet.ServletFileUpload,java.io.File"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'upload.jsp' starting page</title>
    <link rel="stylesheet" type="text/css" href="../Css/Admin.css">
    <style type="text/css">
        body {
            margin-top: 0;
            margin-left: 0
        }
    </style>
</head>
<body>
<%
    if (ServletFileUpload.isMultipartContent(request)) {
        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();
// Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List items = upload.parseRequest(request);
            // Process the uploaded items
            for (Object item1 : items) {
                FileItem item = (FileItem) item1;

                if (!item.isFormField()) {
                    String contentType = item.getContentType();
                    long sizeInBytes = item.getSize();
                    if ((contentType.equals("image/gif") || contentType.equals("image/pjpeg")) && sizeInBytes <= 1024 * 1024) {
                        // Process a file upload
                        String appendix;
                        if ("image/gif".equals(contentType)) {
                            appendix = ".gif";
                        } else {
                            appendix = ".jpg";
                        }
                        String path = request.getServletContext().getRealPath("/UploadImg") + "/";
                        String newFileName = new Date().getTime() + Math.round(Math.random() * 100) + appendix;
                        File uploadedFile = new File(path + newFileName);
                        item.write(uploadedFile);
                        out.println("<script>window.parent.document.getElementById('imgfilepath').value='" + newFileName + "';</script>");
                        out.println("<font size=2>上传成功！ <a href=# onclick='history.back()'>重新上传</a></font>");
                        return;
                    } else {
                        out.println("<script>alert('文件格式仅允许GIF或JPG，大小限制在1M内!');history.back();</script>");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<form method="post" action="upload.jsp" enctype="multipart/form-data">
    <span style="font-size: x-small; ">&nbsp;选择文件：</span>
    <input type="file" name="text1">&nbsp;<input type="submit" name="button1" value="上传">
</form>
</body>
</html>
