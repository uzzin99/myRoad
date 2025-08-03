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
            document.getElementById('eventEnd').value = info.startStr;

            // 모달 열기
            //document.getElementById('eventModal').style.display = 'block';
            const modal = new bootstrap.Modal(document.getElementById('eventModal'));
            modal.show();
        },
        eventClick: function (info) {
            const eventId = info.event.id;  // 이벤트의 고유 id

            axios.get(`/api/schedule/${eventId}`)
                .then(response => {
                    const event = response.data;

                    // 모달에 상세 데이터 세팅
                    document.getElementById('eventTitle').value = event.title;
                    document.getElementById('eventDesc').value = event.description || '';
                    document.getElementById('eventStart').value = event.startDate;
                    document.getElementById('eventEnd').value = event.endDate;

                     // 이벤트 수정용 id 저장
                    document.getElementById('eventId').value = event.id;

                    document.getElementById('myScheduleTitle').innerText = '일정 수정';

                    // 모달 열기
                    //document.getElementById('eventModal').style.display = 'block';
                    const modal = new bootstrap.Modal(document.getElementById('eventModal'));
                    modal.show();
                })
                .catch(error => {
                    console.error('상세 일정 불러오기 실패:', error);
                    alert('일정 상세정보를 불러오는데 실패했습니다.');
                });


        }
    });

    calendar.render();

    // ✅ 동적으로 MySQL 데이터 로딩
    axios.get('/api/schedule/list')
        .then(response => {
            response.data.forEach(event => {
                // allDay용 end는 exclusive이므로, 백엔드가 포함된 종료일(endDate)을 준다면 하루를 더한다.
                let adjustedEnd = null;
                if (event.endDate) {
                    adjustedEnd = new Date(event.endDate);
                    adjustedEnd.setDate(adjustedEnd.getDate() + 1);
                }

                calendar.addEvent({
                    id : event.id,
                    title: event.title,
                    start: event.startDate,
                    end: adjustedEnd,
                    allDay: true,
                    extendedProps: {
                        description: event.description
                    }
                });
            });
        })
        .catch(error => {
            console.error('일정 불러오기 실패:', error);
            alert('일정을 불러오지 못했습니다.');
        });
});

// 저장 버튼
document.getElementById('saveEventBtn').addEventListener('click', function () {
    const id = document.getElementById('eventId').value;
    const title = document.getElementById('eventTitle').value;
    const desc = document.getElementById('eventDesc').value;
    const start = document.getElementById('eventStart').value;
    const end = document.getElementById('eventEnd').value;

    const eventData = {
        title: title,
        description: desc,
        startDate: start,
        endDate: end
    };

    if (id) {
        axios.put(`/api/schedule/${id}`, eventData).then(response => {
            const updated = response.data;
            const event = calendar.getEventById(id);
            if (event) {
                const start = new Date(updated.startDate);
                const end = new Date(updated.endDate);
                end.setDate(end.getDate() + 1);  // allDay 이벤트일 경우 필요

                event.setProp('title', updated.title);
                event.setExtendedProp('description', updated.description);
                event.setStart(start);
                event.setEnd(end);  // 보정된 값
            }
            resetModal();
        })
        .catch(error => {
            console.error('이벤트 수정 중 오류:', error);
            alert('이벤트 수정에 실패했습니다.');
        });

    } else {
        axios.post('/api/schedule/create', eventData).then(response => {
            // 요청 성공 시, 받은 이벤트 데이터로 캘린더에 추가
            const savedEvent = response.data;

            // allDay event의 경우 end는 exclusive이므로 하루를 더해줘야 정확히 원하는 종료일이 된다
            let endDate = null;
            if (savedEvent.endDate) {
              endDate = new Date(savedEvent.endDate);
              endDate.setDate(endDate.getDate() + 1);
            }

            calendar.addEvent({
                id: savedEvent.id,
                title: savedEvent.title,
                start: savedEvent.startDate,
                end: endDate,
                allDay: true,
                extendedProps: {
                    description: savedEvent.description
                }
            });

            resetModal();
        })
        .catch(error => {
            console.error('이벤트 저장 중 오류 발생:', error);
            alert('이벤트 저장에 실패했습니다. 다시 시도해주세요.');
        });
    }

});

// 취소 버튼
document.getElementById('cancelEventBtn').addEventListener('click', function () {
    resetModal();
});

// 모달 초기화
function resetModal() {
    // 모달 닫기
    const modalEl = document.getElementById('eventModal');
    const modal = bootstrap.Modal.getInstance(modalEl); // 기존에 열려있는 모달 인스턴스 가져오기
    if (modal) {
        modal.hide();
    }
    document.getElementById('myScheduleTitle').innerText = '일정 추가';
    document.getElementById('eventTitle').value = '';
    document.getElementById('eventDesc').value = '';
    document.getElementById('eventStart').value = '';
    document.getElementById('eventEnd').value = '';
    document.getElementById('eventId').value = ''; // 수정용 hidden
}
