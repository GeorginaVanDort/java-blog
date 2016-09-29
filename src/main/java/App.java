import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("blogs", Blog.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/blog/:blog_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int blog_id = Integer.parseInt(request.params(":blog_id"));
      Blog blog = Blog.find(blog_id);
      model.put("tags", blog.getTagsByBlog());
      model.put("blogs", blog);
      model.put("template", "templates/blog.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tag/:tag_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int tag_id = Integer.parseInt(request.params(":tag_id"));
      Tag tag = Tag.find(tag_id);
      model.put("tags", tag);
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tags", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tags", Tag.all());
      model.put("template", "templates/tags.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newblog", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tags", Tag.all());
      model.put("template", "templates/newblog.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newblog", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String content = request.queryParams("content");
      Tag tag = Tag.find(Integer.parseInt(request.queryParams("tag")));
      Blog blog = new Blog(name, content);
      blog.save();
      blog.addTag(tag);
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
