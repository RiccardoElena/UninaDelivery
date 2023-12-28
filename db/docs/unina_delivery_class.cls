\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{unina_delivery_class}[2023/12/01 Unina Delivery Class]
\LoadClass[12pt]{extreport}
% Packages
\usepackage{graphicx}  % Required for including images
\usepackage[a4paper, width=180mm, bottom=25mm, top=25mm, bindingoffset=6mm]{geometry} % Page margins
\usepackage{fancyhdr}  % Headers and footers
\pagestyle{fancy} % All pages have headers and footers
\usepackage[defaultcolors]{xcolor} % Required for specifying colors by name
\usepackage[italian]{babel} % Italian language
\usepackage[utf8]{inputenc} % Required for including letters with accents
\usepackage{amsmath, amssymb, amsthm, amsfonts} % Required for math
\usepackage{mathtools} % Required for math
\usepackage[hidelinks]{hyperref} % Required for links
\usepackage{afterpage} % Required for color pages 
\usepackage{titlesec} % Required for titles
\usepackage[most]{tcolorbox} % Required for boxes
\usepackage{tabularx} % Required for tables
\usepackage{colortbl} % Change color of table elements
\usepackage{ifthen} % Required for if-then-else
\usepackage{xargs} % Required for more than one optional parameter in new commands
\usepackage{soul} % Required for fancy text highlighting
\usepackage{float} % Required for forcing floating objects in position
\usepackage{listings, lstautogobble, lstfiracode} % Required for code listings
\usepackage[no-math]{fontspec} % Required for custom font

% Global variables
  \gdef\maintitle{\textcolor{PRIMARY}{Project Title}}
  \gdef\authors{Riccardo Elena --- Gennaro De Gregorio}

% Default color
  \definecolor{PRIMARY}{HTML}{cc4400}
  \definecolor{SECONDARY}{HTML}{f7dba7}
  \definecolor{TERTIARY}{HTML}{191919}
  \definecolor{CONTRAST}{HTML}{6C8EBF}
  \definecolor{NEWGREEN}{HTML}{228231} 
  \definecolor{NEWPINK}{HTML}{FC79C6}


  \color{TERTIARY}

% Graphics path
  \graphicspath{{./sources/}}

% Titles format

  % Chapters
  \titleformat{\chapter}[block]
    {\normalfont\huge\bfseries\color{PRIMARY}}
    {\ \textcolor{black}{\thechapter}}{20pt}{\Huge}
    [\vspace{0.5ex}\titlerule]
  \titlespacing*{\chapter}{0pt}{-50pt}{20pt}

  % Sections
  \titleformat{\section}[block]
    {\normalfont\large\bfseries\color{PRIMARY}}
    {\ \textcolor{black}{\thesection}}{20pt}{\Large}

  % Subsections
  \titleformat{\subsection}[block]
    {\normalfont\normalsize\bfseries\color{PRIMARY}}
    {\ \textcolor{black}{\thesubsection}}{20pt}{\large}

  % Subsubsections
  \titleformat{\subsubsection}[block]
    {\normalfont\normalsize\bfseries\color{PRIMARY}}
    {\textbullet}{20pt}{\normalsize}


% Header and footer
  
  \setlength{\headheight}{16.20004pt}
  \addtolength{\topmargin}{-2.20004pt}
  % Choosing info to display in the header
  \renewcommand{\chaptermark}[1]{\markboth{\normalsize \textcolor{PRIMARY}{ \large #1}}{}}
  \renewcommand{\sectionmark}[1]{\markright{\normalsize \textcolor{PRIMARY}{\thesection} \hspace{2 pt} \large \textcolor{TERTIARY}{#1}}{}}

  % Header and footer style
  \fancyhf{}
  \fancyhead[L]{\small\rightmark}
  \fancyhead[R]{\small\leftmark}
  \fancyfoot[L]{\textcolor{PRIMARY}{\maintitle}}
  \fancyfoot[C]{\textcolor{black}{\thepage}}
  \fancyfoot[R]{\textcolor{PRIMARY}{\footnotesize\authors}}
  \let\oldheadrule=\headrule{}

  \renewcommand{\headrule}{\color{PRIMARY}\oldheadrule}
  \renewcommand{\headrulewidth}{0.5pt}
  \let\oldfootrule=\footrule{}

  \renewcommand{\footrule}{\color{PRIMARY}\oldfootrule}
  \renewcommand{\footrulewidth}{0.5pt}

% Setting paragraph style
  \setlength\parindent{0pt}
  \setlength\parskip{12pt}

% Setting itemize style
  \renewcommand{\labelitemi}{\textcolor{PRIMARY}{\textbullet}}

  \renewcommand{\labelitemii}{\textcolor{SECONDARY}{\textbullet}}

  \renewcommand{\labelitemiii}{\textcolor{TERTIARY}{-}}

% Setting custom bold text color
\let\textbfB\textbf
\renewcommand{\textbf}[1]{\textcolor{PRIMARY}{\textbfB{#1}}}

% Custom box definition
\newtcolorbox[auto counter]{note}[1][]{
  enhanced,
  breakable,
  colback=SECONDARY!70!white,
  colframe=PRIMARY!70!white,
  drop fuzzy shadow,
  attach boxed title to top left={yshift*=-\tcboxedtitleheight},
  title= Nota \thetcbcounter: #1,
  boxrule=0.5pt,  % riduce lo spessore del bordo del box
  toptitle=1mm,  % riduce lo spazio sopra il titolo
  bottomtitle=1mm,  % riduce lo spazio sotto il titolo
  boxed title style={
    rounded corners,
    sharp corners=northeast,
    colback=tcbcolframe,
    boxrule=0pt,
  }
}

% Custom table definition
\newcolumntype{Y}{>{\centering\arraybackslash}X}
\newcommandx{\customTable}[5][2={},3={}]{
  \renewcommand{\arraystretch}{2.1}
  \arrayrulecolor{PRIMARY}
  \begin{table}[H]
    \ifthenelse{\equal{#3}{}}{}{\label{#3}}
    \centering
    \begin{tabularx}{\textwidth}{#1}
    \hline
    \rowcolor{SECONDARY} #4 \\
    \hline
    #5
    \hline
    \end{tabularx}
    \ifthenelse{\equal{#2}{}}{}{\caption{#2}}
  \end{table}
} 
  % Invoke this as \customTable{table format}[table caption][table label]{table head}{table content}
  % table head should be a list of column names separated by &
  % the arguments in square brackets are optional, if you don't need them square brackets can be omitted


\newcommandx{\customTableLogic}[6][2={},3={}]{
  \begin{table}[H]
    \ifthenelse{\equal{#3}{}}{}{\label{#3}}
  \centering
  \begin{tabularx}{\textwidth}{#1}
    \hline
    \multicolumn{#4}{|c|}{\large\textbf{#5}} \\
    \hline
    #6 \\
    \hline
  \end{tabularx}
\end{table}
}

% Custom link definition
\newcommand{\exlink}[2]{\setulcolor{PRIMARY}\href{#1}{\ul{\textit{#2}}}}
\newcommand{\intlink}[2]{\setulcolor{SECONDARY}\hyperref[#1]{\ul{\textit{#2}}}}

% Icluding FiraCode font
\newfontfamily\FiraCode{FiraCode}[
                Path = fonts/,
                Extension = .ttf,
                UprightFont = *-Regular,
                BoldFont = *-Bold,
                Contextuals=Alternate
        ]

% Custom theme for code listings
\renewcommand{\lstlistingname}{Codice}
\renewcommand\lstlistlistingname{Frammenti di Codice}
\lstdefinelanguage{PSQL}{
  autogobble=true,
  mathescape=false,
  captionpos=b,
  basicstyle=\linespread{1.2}\color{white!80!gray}\FiraCode\footnotesize,
  numbers=left,
  numberstyle=\color{CONTRAST},
  xleftmargin=1cm,
  frame=l,
  framesep=1cm,
  framexleftmargin=1cm,
  stepnumber=1,
  numbersep=10pt,
  tabsize=2,
  extendedchars=true,
  breaklines=true,
  % Comments
  morecomment=[l][\color{yellow}]{--},
  morecomment=[l][\color{SECONDARY}]\$\$,
  % Strings
  string=[s]{'}{'},
  stringstyle=\color{NEWGREEN},
  % Keywords
    %% all keywords ahve to be added here
    alsoletter={*, ADD, SELECT, FROM, WHERE, GROUP, BY, BEG, HAVING, ORDER, ASC, DESC, CASCADE, LIMIT, OFFSET, AS, INNER, JOIN, ON, LEFT, RIGHT, FULL, OUTER, UNION, ALL, NOT, NULL, IS, DISTINCT, CASE, FUNCTI, TRIGGER, WHEN, THEN, ELSE, END, CAST, IN, ANY, SOME, EXISTS, INSERT, INTO, VALUES, UPDATE, SET, DELETE, TRUNCATE, CREATE, TABLE, PRIMARY, KEY, FOREIGN, REFERENCES, CHECK, CONSTRAINT, UNIQUE, DEFAULT, ALTER, DROP, INDEX, VIEW, SEQUENCE, TRIGGER, FUNCTION, PROCEDURE, RETURNS, RETURN, TYPE, ENUM, DOMAIN, AGGREGATE, COLLATE, EXTENSION, IF, EXISTS, OWNER, MODE, TO, PUBLIC, GRANT, REVOKE, ROLE, USER, PASSWORD, LOGIN, CONNECTION, LIMIT, SESSION, ROLE, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, integer, boolean, float, text, varchar, char, date, time, timestamp, interval, numeric, money, bit, bytea, serial, bigserial, smallserial, smallint, bigint, real, double, precision, decimal, OR, REPLACE, FUNCTION, RETURNS, TABLE, BEGIN, END, LOOP, WHILE, FOR, DECLARE},
    %% numeric keywords
    keywords=[0]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
    %% language keywords
    keywords=[1]{*, ADD, DATABASE, SELECT, FROM, WHERE, GROUP, BY, BEG, HAVING, ORDER, ASC, CASCADE, DESC, LIMIT, OFFSET, AS, INNER, JOIN, ON, LEFT, RIGHT, FULL, OUTER, UNION, ALL, NOT, NULL, IS, DISTINCT, CASE, FUNCTI, TRIGGER, WHEN, THEN, ELSE, END, CAST, IN, ANY, SOME, EXISTS, INSERT, INTO, VALUES, UPDATE, SET, DELETE, TRUNCATE, CREATE, TABLE, PRIMARY, KEY, FOREIGN, REFERENCES, CHECK, CONSTRAINT, UNIQUE, DEFAULT, ALTER, DROP, INDEX, VIEW, SEQUENCE, TRIGGER, FUNCTION, PROCEDURE, RETURNS, RETURN, TYPE, ENUM, DOMAIN, AGGREGATE, COLLATE, EXTENSION, IF, EXISTS, OWNER, MODE, TO, PUBLIC, GRANT, REVOKE, ROLE, USER, PASSWORD, LOGIN, CONNECTION, LIMIT, SESSION, ROLE, OR, REPLACE, FUNCTION, RETURNS, TABLE, BEGIN, END, LOOP, WHILE, FOR, DECLARE},
    %% data type keywords
    keywords=[2]{integer, boolean, float, text, varchar, char, date, time, timestamp, interval, numeric, money, bit, bytea, serial, bigserial, smallserial, smallint, bigint, real, double, precision, decimal},
    %% Colors
    keywordstyle=[0]\color{CONTRAST},
    keywordstyle=[1]\color{PRIMARY},
    keywordstyle=[2]\color{NEWPINK},
    keywordstyle=\color{PRIMARY},
  backgroundcolor=\color{TERTIARY!90!violet},
  xleftmargin=2.5em,
  showspaces=false,
  showstringspaces=false,
  showtabs=false,
  frame=none
}

\lstset{language=PSQL}