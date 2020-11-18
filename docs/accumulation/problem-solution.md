# Problem Solution

文本记录开发过程中出现的问题的以及解决方案。



## 1、java

### 1.1 线程

(1) 收到回调之后，执行完成后，服务直接挂掉，没有任何报错信息

时间： 2020-10-30

打包之后，发现收到一次回调之后 时候在不断的 new Thread，最终 



## 2、mysql



##  3、前端

### 3.1 vue

**(1)  element-ui 版本从 2.8 升级到 2.14.0 之后，打包发布之后 icon 缺失**

[ 解决部署到服务器后Element UI图标不显示问题](https://www.niwoxuexi.com/blog/hangge/article/634.html)

问题的原因：

1）查看 **/build/webpack.base.conf.js** 文件可以发现，**woff** 或 **ttf** 这些字体会经由 **url-loader** 处理后在 **static/fonts** 目录下生成相应的文件。

2） 也就是说实际应该通过 **/static/fonts/\**** 路径来获取字体图标，而实际却是请求 **/static/css/static/fonts/\****，自然报 **404** 错误。

解决方案:

打开 **build/utils.js** 文件，在如下位置添加 **publicPath: '../../'**

```js
  if (options.extract) {
      return ExtractTextPlugin.extract({
        use: loaders,
        fallback: 'vue-style-loader',
        publicPath: '../../'
      })
    } else {
      return ['vue-style-loader'].concat(loaders)
    }
```

