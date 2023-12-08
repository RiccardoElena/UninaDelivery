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
\usepackage{tcolorbox} % Required for boxes

% Global variables
  \gdef\maintitle{\textcolor{PRIMARY}{Project Title}}
  \gdef\authors{Riccardo Elena --- Gennaro De Gregorio}

% Default color
  \definecolor{PRIMARY}{HTML}{cc4400}
  \definecolor{SECONDARY}{HTML}{f7dba7}
  \definecolor{TERTIARY}{HTML}{191919}
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
  % FIXME: @RiccardoElena fix subsection title format to display a dot
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
% TODO: @RiccardoElena improve boc style to reduce colframe size
\newtcolorbox[auto counter]{note}[1][]{
  colback=SECONDARY!70!white,
  colframe=PRIMARY!70!white,
  title= Nota \thetcbcounter: #1,
  boxrule=0.5pt,  % riduce lo spessore del bordo del box
  toptitle=1mm,  % riduce lo spazio sopra il titolo
  bottomtitle=1mm,  % riduce lo spazio sotto il titolo
}