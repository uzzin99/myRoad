/**
 * 사이드바/네비게이션에서 클릭된 <a.nav-link>에 active를 붙이고
 * URL 기준으로 자동으로 active를 설정하는 유틸
 *
 * 사용법:
 *   NavActive.init({
 *     navContainerSelector: ".navbar-nav", // nav 링크들을 감싼 컨테이너
 *     linkSelector: ".nav-link",           // active를 토글할 링크들
 *     activeClass: "active"                // 사용할 클래스 이름
 *   });
 */
const NavActive = (function () {
  function init(options = {}) {
    const {
      navContainerSelector = ".navbar-nav",
      linkSelector = ".nav-link",
      activeClass = "active",
      highlightPartialMatch = true // 부분 매칭도 허용할지
    } = options;

    const container = document.querySelector(navContainerSelector);
    if (!container) return;

    const links = container.querySelectorAll(linkSelector);
    if (!links.length) return;

    // 클릭 시 active 토글
    links.forEach(link => {
      link.addEventListener("click", (e) => {
        links.forEach(l => l.classList.remove(activeClass));
        link.classList.add(activeClass);
      });
    });

    // 현재 URL 기준으로 초기 active 설정
    const currentPath = window.location.pathname;
    let matched = false;

    links.forEach(link => {
      const hrefAttr = link.getAttribute("href");
      if (!hrefAttr) return;
      let hrefPath;
      try {
        hrefPath = new URL(hrefAttr, window.location.origin).pathname;
      } catch {
        return; // 무효한 href
      }

      if (hrefPath === currentPath) {
        links.forEach(l => l.classList.remove(activeClass));
        link.classList.add(activeClass);
        matched = true;
      }
    });

    if (!matched && highlightPartialMatch) {
      let bestMatch = null;
      let bestLen = 0;
      links.forEach(link => {
        const hrefAttr = link.getAttribute("href");
        if (!hrefAttr) return;
        let hrefPath;
        try {
          hrefPath = new URL(hrefAttr, window.location.origin).pathname;
        } catch {
          return;
        }

        if (currentPath.startsWith(hrefPath) && hrefPath.length > bestLen) {
          bestLen = hrefPath.length;
          bestMatch = link;
        }
      });
      if (bestMatch) {
        links.forEach(l => l.classList.remove(activeClass));
        bestMatch.classList.add(activeClass);
      }
    }
  }

  return { init };
})();
