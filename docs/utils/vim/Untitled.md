

[原文](https://www.ravenxrz.ink/archives/9cf25d6b.html)

# ideavim 配置说明

 发表于 2020-02-14  更新于 2021-05-16  分类于 [杂文](https://www.ravenxrz.ink/categories/杂文/)

> 由于 hexo 的渲染机制，本文代码有问题，所以选择隐藏。

作为一个半 vimer：不想用纯 vim + 各种插件，但实现很喜欢 vim 的各种键位带来的便捷性。所以很久以前就是使用 IDE+vim 插件在使用。IDE 当然是使用 jetbrain 家的了。今天就来介绍一下 jb 家可用的 vim 插件 ideavim 以及如何自定义配置 ideavim。

hexo 显示尖括号有问题，导向 github:

[https://github.com/ravenxrz/ravenxrz.github.io/blob/master/source/_posts/ideavim%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E.md](https://github.com/ravenxrz/ravenxrz.github.io/blob/master/source/_posts/ideavim配置说明.md)



## 1. 安装 ideavim

安装就很简单了，File->Settings->Plugins 即可。

如下图：

![img]()

安装后重启，默认就是 vim 了。vim 的各种基础功能都可以正常使用。

下面来讲一下如何自定义适合自己的 vim 操作。

## 2. ideavimrc 配置

如果你是 Windows 用户，请在当前用户的根目录下，建立.ideavimrc 文件，如我的计算机用户名是 Raven，则创建路劲如下：

![img]()

如果是 mac/linux 用户，则创建路径为～/.ideavimrc。

创建好后，打开该文件，我们可对其进行一些通用的设置（为什么这里说是通用呢？因为 ideavim 可绑定 ide 的一些功能来使用）。如:

- 设置相对行号: `set number`
- 开启增量搜索：`set incsearch`
- 开启搜索高亮: `set hlsearch`
- …

我的基础配置项如下，可根据个人使用习惯进行配置：

```
" 显示当前模式
set showmode
" 共享系统粘贴板
set clipborad=unamed
" 打开行号
set number
" 打开相对行号
" set relativenumber
" 设置命令历史记录条数
set history=2000
" 关闭兼容vi
set nocompatible
" 开启语法高亮功能
syntax enable
" 允许用指定语法高亮配色方案替换默认方案
syntax on
" 模式搜索实时预览,增量搜索
set incsearch
" 设置搜索高亮
set hlsearch
" 忽略大小写 (该命令配合smartcase使用较好，否则不要开启)
set ignorecase
" 模式查找时智能忽略大小写
set smartcase
" vim自身命令行模式智能补全
set wildmenu
" 总是显示状态栏
set laststatus=2
" 显示光标当前位置
set ruler
" 高亮显示当前行/列
set cursorline
"set cursorcolumn
" 禁止折行
set nowrap
" 将制表符扩展为空格
set expandtab
" 设置编辑时制表符占用空格数
set tabstop=8
" 设置格式化时制表符占用空格数
set shiftwidth=4
" 让 vim 把连续数量的空格视为一个制表符
set softtabstop=4
" 基于缩进或语法进行代码折叠
set foldmethod=indent
set foldmethod=syntax
" 启动 vim 时关闭折叠代码
set nofoldenable
```

### 2.1 leader 键

现在开始讲第一个重点，**leader 键**。稍微熟悉一点 vim 肯定是知道 leader 键的。leader 键的作用相当于扩展了一个功能键位，使用 leader 键 + 其它组合键能够避免原来基础 vim 下的键位冲突。这样解释肯定让人感到很迷，简单的说来就是你设置一个 leader 键（比如 分号；)，然后通过这个分号 + 其它键可以映射成各种功能。举个例子：

设置 leader 键：

```
let mapleader=";"
```

设置，通过 leader (即；)+t 实现跳转 (Ctrl + ])

```
nmap <Leader>t <C-]>
```

现在你按；+ t 就等同于 Ctrl +］。

这样可以自主映射成许多功能了，如：

- 取消搜索后的高亮：`nnoremap <silent> <Leader>l :<C-u>nohlsearch<CR><C-l>`
- 跳转到上 / 下一个缓冲区（当然这个在 ideavim 中没有，纯 vim 中是有用的）:`nnoremap <silent> [b :w<CR>:bprevious<CR>` `nnoremap <silent> ]b :w<CR>:bnext<CR>`
- …

### 2.2 配合 IDE 的 action

我们可以配置 ideavim，使得 ideavim 可以调用 ide 的各种各种。如 run，debug，格式化代码等等。

ide 的各个功能都对应有一个 action，如格式化代码对应 ->`ReformatCode`，我们可以这样设置，将 leader+f（按个人需要设置相关映射) 和 ReformatCode 绑定在一起:

```
nnoremap <Leader>f :action ReformatCode<CR>
```

怎么去查这些 action 呢，输入:actionlist 即可。将得到如下图：

![img]()

或者你可以这个链接里面找相关的 action，https://gist.github.com/ravenxrz/e5786b0a5e32fb8b2208bf50e314ab5b

下面给我的常用 action 映射配置：

```
" 使用idea内部功能
" copy operation
nnoremap <Leader>c :action $Copy<CR>
" paste operation
nnoremap <Leader>v :action $Paste<CR>
" cut operation
nnoremap <Leader>x :action $Cut<CR>
" Select All
nnoremap <Leader>a :action $SelectAll<CR>
" reformat code
nnoremap <Leader>f :action ReformatCode<CR>
" New File
nnoremap <Leader>n :action NewFile<CR>
" 找到usage
nnoremap <Leader>u :action FindUsages<CR>
" 调用idea的replace操作
nnoremap <Leader>; :action Replace<CR>
" go to class
nnoremap <Leader>gc :action GotoClass<CR>
" go to action
nnoremap <Leader>ga :action GotoAction<CR>
" run
nnoremap <Leader>r :action Run<CR>
" 显示当前文件的文件路径
nnoremap <Leader>fp :action ShowFilePath<CR>
" 隐藏激活窗口
nnoremap <Leader>h :action HideActiveWindow<CR>
```

### 2.3 ideavim 的附加插件

ideavim 中还可以开启其余四个插件。（其实是模拟的）

- easymotion（这个插件需要额外安装[ IdeaVim-EasyMotion](https://plugins.jetbrains.com/plugin/13360-ideavim-easymotion/) 和[ AceJump](https://plugins.jetbrains.com/plugin/7086-acejump/) ）
- surround
- multiple-cursors
- commentary

要开启任一个插件，只需要在.ideavimrc 中输入 set xxx (如 set surround)。

如何设置和使用，这里就粘贴官网的说明了：

- easymotion
  - Setup:
    - Install [IdeaVim-EasyMotion](https://plugins.jetbrains.com/plugin/13360-ideavim-easymotion/) and [AceJump](https://plugins.jetbrains.com/plugin/7086-acejump/) plugins.
    - `set easymotion`
  - Emulates [vim-easymotion](https://github.com/easymotion/vim-easymotion)
  - Commands: All commands with the mappings are supported. See the [full list of supported commands](https://github.com/AlexPl292/IdeaVim-EasyMotion#supported-commands).
- surround
  - Setup: `set surround`
  - Emulates [vim-surround](https://github.com/tpope/vim-surround)
  - Commands: `ys`, `cs`, `ds`, `S`
- multiple-cursors
  - Setup: `set multiple-cursors`
  - Emulates [vim-multiple-cursors](https://github.com/terryma/vim-multiple-cursors)
  - Commands: `<A-n>`, `<A-x>`, `<A-p>`, `g<A-n>`
- commentary
  - Setup: `set commentary`
  - Emulates [commentary.vim](https://github.com/tpope/vim-commentary)
  - Commands: `gcc`, `gc + motion`, `v_gc`

就我个人而言，这几个插件能带来的效率都不算高。

### 2.4 解决烦人的中英文切换问题

经常使用 vim 的人，一定会遇到中英文切换的问题。这里使用 IdeaVimExtension 插件可解决：

![img]()

安装该插件后，在 ideavimrc 中输入：

```
set keep-english-in-normal-and-restore-in-insert
```

## 3. 所有配置

我的所有配置如下：

```
" 显示当前模式
set showmode
" 共享系统粘贴板
set clipborad=unamed
" 打开行号
set number
" 打开相对行号
" set relativenumber
" 设置命令历史记录条数
set history=2000
" 关闭兼容vi
set nocompatible
" 开启语法高亮功能
syntax enable
" 允许用指定语法高亮配色方案替换默认方案
syntax on
" 模式搜索实时预览,增量搜索
set incsearch
" 设置搜索高亮
set hlsearch
" 忽略大小写 (该命令配合smartcase使用较好，否则不要开启)
set ignorecase
" 模式查找时智能忽略大小写
set smartcase
" vim自身命令行模式智能补全
set wildmenu
" 总是显示状态栏
set laststatus=2
" 显示光标当前位置
set ruler
" 高亮显示当前行/列
set cursorline
"set cursorcolumn
" 禁止折行
set nowrap
" 将制表符扩展为空格
set expandtab
" 设置编辑时制表符占用空格数
set tabstop=8
" 设置格式化时制表符占用空格数
set shiftwidth=4
" 让 vim 把连续数量的空格视为一个制表符
set softtabstop=4
" 基于缩进或语法进行代码折叠
set foldmethod=indent
set foldmethod=syntax
" 启动 vim 时关闭折叠代码
set nofoldenable

" 设置前导键
let mapleader=";"
" 暂时取消搜索高亮快捷键
nnoremap <silent> <Leader>l :<C-u>nohlsearch<CR><C-l>

" 移动相关
" 前一个缓冲区
nnoremap <silent> [b :w<CR>:bprevious<CR>
" 后一个缓冲区
nnoremap <silent> ]b :w<CR>:bnext<CR>
" 定义快捷键到行首和行尾
map H ^
map L $
" 定义快速跳转
nmap <Leader>t <C-]>
" 定义快速跳转回退
nmap <Leader>T <C-t>
" 标签页后退 ---标签页前进是gt
nmap gn gt
nmap gp gT

" 文件操作相关
" 定义快捷键关闭当前分割窗口
nmap <Leader>q :q<CR>
" 定义快捷键保存当前窗口内容
nmap <Leader>w :w<CR>

" 窗口操作相关
map <C-j> <C-W>j
map <C-k> <C-W>k
map <C-h> <C-W>h
map <C-l> <C-W>l

" 使用idea内部功能
" copy operation
nnoremap <Leader>c :action $Copy<CR>
" paste operation
nnoremap <Leader>v :action $Paste<CR>
" cut operation
nnoremap <Leader>x :action $Cut<CR>
" Select All
nnoremap <Leader>a :action $SelectAll<CR>
" reformat code
nnoremap <Leader>f :action ReformatCode<CR>
" New File
nnoremap <Leader>n :action NewFile<CR>
" 找到usage
nnoremap <Leader>u :action FindUsages<CR>
" 调用idea的replace操作
nnoremap <Leader>; :action Replace<CR>
" go to class
nnoremap <Leader>gc :action GotoClass<CR>
" go to action
nnoremap <Leader>ga :action GotoAction<CR>
" run
nnoremap <Leader>r :action RunClass<CR>
" 显示当前文件的文件路径
nnoremap <Leader>fp :action ShowFilePath<CR>
" 隐藏激活窗口
nnoremap <Leader>h :action HideActiveWindow<CR>

" 中英文自动切换
set keep-english-in-normal-and-restore-in-insert

" other vim plugins
" comment plugin
set commentary
" surround plugin
set surround
" easymotion
set easymotion

" 一些有用的快捷键，但是没做映射
" open project file tree ---------- alt + 1
" open terminal window   ---------- alt + F12
```

文章对你有帮助？打赏一下作者吧

打赏

- **本文作者：** Raven
- **本文链接：** https://www.ravenxrz.ink/archives/9cf25d6b.html
- **版权声明：** 本博客所有文章除特别声明外，均采用 [BY-NC-SA](https://creativecommons.org/licenses/by-nc-sa/4.0/) 许可协议。转载请注明出处！

[ vim](https://www.ravenxrz.ink/tags/vim/) [ idea](https://www.ravenxrz.ink/tags/idea/) [ ideavim](https://www.ravenxrz.ink/tags/ideavim/)

[ 解决 Windows 高分屏字体模糊问题](https://www.ravenxrz.ink/archives/bd948cbe.html)

[a start job is running for dev-disk-by… 问题解决 ](https://www.ravenxrz.ink/archives/d633803a.html)

 

- 文章目录
-  

- 站点概览

1. [1. 安装 ideavim](https://www.ravenxrz.ink/archives/9cf25d6b.html#1-安装ideavim)
2. \2. ideavimrc 配置
   1. [2.1 leader 键](https://www.ravenxrz.ink/archives/9cf25d6b.html#2-1-leader键)
   2. [2.2 配合 IDE 的 action](https://www.ravenxrz.ink/archives/9cf25d6b.html#2-2-配合IDE的action)
   3. [2.3 ideavim 的附加插件](https://www.ravenxrz.ink/archives/9cf25d6b.html#2-3-ideavim的附加插件)
   4. [2.4 解决烦人的中英文切换问题](https://www.ravenxrz.ink/archives/9cf25d6b.html#2-4-解决烦人的中英文切换问题)
3. [3. 所有配置](https://www.ravenxrz.ink/archives/9cf25d6b.html#3-所有配置)

© 2017 – 2021 Raven

由 [Hexo](https://hexo.io/) & [NexT.Mist](https://mist.theme-next.org/) 强力驱动