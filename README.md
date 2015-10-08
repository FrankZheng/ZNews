ZNews-iOS (A Google News Reader App for Android)
=========
About
---------
A news reader app, has a news list and news detail screen, the news source is from Google news.
The backend will fetch RSS feed from google news periodically, do parsing and abstracting the news real content, then save to the database. 


The backend architecture & technologies
---------
The backend is hosted on Heroku, include 3 modules:
* xnewsreader -- a web server, providing APIs for client app, implementing by Node.js and Express.
* xnewscrawler -- a background worker, fetch RSS feeds and save to the database periodically, implementing by Node.js.
* xnewsextractor -- a background worker, abstracting the real content of the news and save to the database periodically, implementing by Python.
* The database is a mongodb instance hosted on mongolab.

The backend API
----------
http://xnewsreader.herokuapp.com/articles?lang=en&limit=50&topic=t&before=2015-10-04T00:47:45.000&output=json
* lang [zh | en] -- language, default is zh. 
* limit -- specified how much news will return, default is all the news by today.
* topic [t] -- specified which topic of the return news, default is the top news.
* before -- specified the date before.
* output [html|json] -- specified output format, default is html.

The Client App technolgies
----------
* ormlite
* okhttp
* Android-Universal-Image-Loader
* dagger

Todo list:
-----------
* Support multiple topics.
* Code cleanup and refactoring.

Preview
----------
![github](https://raw.githubusercontent.com/FrankZheng/ZNews-iOS/master/screenshots/4.png "github")

![github](https://raw.githubusercontent.com/FrankZheng/ZNews-iOS/master/screenshots/5.png "github")
