function update(roomId) {
  var headerAvailable = $('#headerAvailable');
  var iconAvailable = $('#iconAvailable');
  var iconBusy = $('#iconBusy');
  var headerBusy = $('#headerBusy');

  headerAvailable.hide();
  iconAvailable.hide();
  iconBusy.hide();
  headerBusy.hide();

  $.ajax({
    url: "/meetingrooms/" + roomId + "/availability",
    success: function(result) {

      $("#msgContainer").hide()
      if (!result) {
        return;
      }

      var block;
      var duration = null;
      if (!result.untilNextFree) {
        headerAvailable.show();
        iconAvailable.show();
        block = headerAvailable;
        duration = result.freeFor;
      } else {
        iconBusy.show();
        headerBusy.show();
        block = headerBusy;
        duration = result.untilNextFree;
      }

      var showInfo = !!duration && !!result.meeting;
      var info = block.find(".info");
      var noInfo = block.find(".noInfo");
      info.hide();
      noInfo.hide();
      if (showInfo) {
        info.find(".owner").text(result.meeting.owner);
        info.find(".title").text(result.meeting.title);
        info.find(".duration").text(humanTime(duration));
        info.show();
      } else {
        noInfo.show();
      }


      var slots = result.bookFor;
      var html = "";
      for (var i = 0; i < slots.length; ++i) {
        var slot = slots[i];
        html += '<p><button type="button" id="bookBtn_'+i+'" class="btn btn-primary btn-block btn-lg">Book for '+humanTime(slot.duration)+'</button></p>\n';
      }
      $('#buttons').html(html);
      for (var i = 0; i < slots.length; ++i) {
        (function(idx) {
          $("#bookBtn_" + idx).click(function() {


              $.ajax({
                  type: "POST",
                  url: '/room/'+roomId+'/book',
                  data: slots[idx],
                  success: function(result) {
                    $('#mainContainer').hide()
                    var msg = result != null ? result.message : "An error occurred during booking!"
                    var msgContainer = $("#msgContainer");
                    msgContainer.html(msg);
                    msgContainer.show();
                    setTimeout(function() {
                      update(roomId)
                    }, 2000)

                  }
              });
          })
        })(i)
      }

      $('#mainContainer').show();
    }
  });
}



function humanTime(value) {
  var h = Math.floor(value / 60);
  var m = value % 60;
  var s = ""
  if (h > 0) {
    s += h + " h"
  }
  if (m > 0) {
    s += " " + m + " min"
  }
  return s.trim()
}
