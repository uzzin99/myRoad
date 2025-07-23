
let calendar;
let selectedInfo = null;

document.addEventListener('DOMContentLoaded', function () {
  const calendarEl = document.getElementById('calendar');

  calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: 'dayGridMonth',
    selectable: true,
    contentHeight: 600,
    locale: "ko",
    select: function (info) {
      selectedInfo = info;

      // 날짜 기본값 설정
      document.getElementById('eventStart').value = info.startStr;
      document.getElementById('eventEnd').value = info.endStr;

      // 모달 열기
      document.getElementById('eventModal').style.display = 'block';
    },
    eventClick: function (info) {
      const event = info.event;
      alert(
        '제목: ' + event.title + '\n' +
        '내용: ' + (event.extendedProps.description || '(없음)') + '\n' +
        '시작일: ' + event.start.toISOString().slice(0, 10) + '\n' +
        '종료일: ' + (event.end ? event.end.toISOString().slice(0, 10) : '(없음)')
      );
    }
  });

  calendar.render();
});

// 저장 버튼
document.getElementById('saveEventBtn').addEventListener('click', function () {
  const title = document.getElementById('eventTitle').value;
  const desc = document.getElementById('eventDesc').value;
  const start = document.getElementById('eventStart').value;
  const end = document.getElementById('eventEnd').value;

  if (title) {
    calendar.addEvent({
      title: title,
      start: start,
      end: end,
      allDay: true,
      extendedProps: {
        description: desc
      }
    });
  }

  // 입력 초기화 및 모달 닫기
  document.getElementById('eventTitle').value = '';
  document.getElementById('eventDesc').value = '';
  document.getElementById('eventStart').value = '';
  document.getElementById('eventEnd').value = '';
  document.getElementById('eventModal').style.display = 'none';
});

// 취소 버튼
document.getElementById('cancelEventBtn').addEventListener('click', function () {
  document.getElementById('eventModal').style.display = 'none';
});