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

% Global variables
  \gdef\maintitle{\textcolor{PRIMARY}{Project Title}}
  \gdef\authors{Riccardo Elena --- Gennaro De Gregorio}

% Default color
  \definecolor{PRIMARY}{HTML}{cc4400}
  \definecolor{SECONDARY}{HTML}{f7dba7}
  \definecolor{TERTIARY}{HTML}{191919}
  \definecolor{CONTRAST}{HTML}{6C8EBF}
  \definecolor{NEWGREEN}{HTML}{228231} 


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
\let\oldtextbf\textbf
\renewcommand{\textbf}[1]{\textcolor{PRIMARY}{\oldtextbf{#1}}}

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

\newcommandx{\customTable}[5][2={},3={}]{
  \renewcommand{\arraystretch}{1.5}
  \newcolumntype{Y}{>{\centering\arraybackslash}X}
  \arrayrulecolor{PRIMARY}
  \begin{table}[h]
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

