ZNews （最新闻 Android 客户端）
=========
关于
---------
一个新闻客户端，实现了新闻列表和新闻内容的浏览。
后台从谷歌新闻抓取RSS，解析，并提取正文内容，保存到后台数据库中。
目前还属于自娱自乐阶段，代码没有经过好的规划和整理。


后台技术架构
---------
后台搭建在heroku上，目前有三个模块
* xnewsreader    一个web服务，对客户端提供json格式的内容。
* xnewscrawler   一个后台worker，定时去谷歌新闻抓取RSS，解析并保存到数据库中
* xnewsextractor 一个后台worker，定时去数据库中解析源新闻网页，提取正文并保存到数据库中。
* 数据库使用mongolab提供的mongodb服务。

后台服务API
----------
http://xnewsreader.herokuapp.com/articles?lang=en&limit=50&topic=t&before=2015-3-28T00:47:45.000&output=json
* 现在默认抓取中文的焦点和科技新闻，英文的焦点和科技新闻。
* lang  [zh | en] 默认为zh 
* limit 用来指定返回多少条新闻，默认为之前一天内的所有新闻
* topic 用来指定新闻的分类，默认为焦点新闻
* before 用来指定时间，可以取回某个时间点之前的新闻
* output [html|json] 用来指定输出格式，默认为html

客户端app架构
----------
* 存储部分使用 ormlite
* 网络部分使用 okhttp
* 图片异步加载，缓存使用 Android-Universal-Image-Loader
* 使用dagger做DI框架


将来考虑实现的功能
-----------
* 支持多栏目新闻浏览和切换
* 代码整理和优化

预览
----------
![github](https://raw.githubusercontent.com/FrankZheng/ZNews-iOS/master/screenshots/4.png "github")

![github](https://raw.githubusercontent.com/FrankZheng/ZNews-iOS/master/screenshots/5.png "github")
