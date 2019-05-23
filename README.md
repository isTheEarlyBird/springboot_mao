设置一下项目jdk为1.8，然后刷新一下maven即可，然后修改yml中的数据库连接相关

前台首页：http://localhost:8080/mao/
后台首页：http://localhost:8080/mao/admin/

注意：打开前台首页时是没有数据的，需要在后台添加

主要功能：<br>
    前台：注册登陆，购物车，下单，所有订单<br>
    后台：shiro进行权限管理（这里只有两种角色，ADMIN和USER），后台用户管理，商品管理，订单管理
          
所用技术：<br>
    tomcat，springboot（thymeleaf），ssm(mysql，druid），shiro，jquery，bootstrap，adminLTE
    
所用开发软件：idea

缺点：<br>
    <b>1.没有以json方式返回数据（接口开发），直接将数据以request(ModelAndView)或session添加属性的方式返回</b><br>
    2.页面css、js重复多（不会处理）<br>
    3.有些细节没有弄（例如删除某个商品时没有弹出确认框等）<br>
    4.商品的分类只弄了一级（没弄分类下再分类）<br>
    5.当后台删除某个商品时，删除不了，应该是数据库设计有问题，<br>
      原因：“客户”下的订单中有该商品，存在外键，所有删除不了，只用了springmvc异常处理简单处理了异常，当然商品还是删除不了，除非把订单删了。<br>
      所以有些删除功能也没弄，例如分类、角色、权限

最后总结：除去前端，然后这个项目难点就在于，环境搭建和增删改查（哭笑脸）
