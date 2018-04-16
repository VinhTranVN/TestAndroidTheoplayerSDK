var theoClasses = {
//    bigPlayButton : '.vjs-big-play-button',
//    controlBar : '.vjs-control-bar',
//    fullscreenControl: '.vjs-fullscreen-control',
//    seekBall: '.vjs-mouse-display',
//    progressControl: '.vjs-progress-control',
//    thumbProgress: '.vjs-play-progress'
}

// Remove Big play button
$(theoClasses.bigPlayButton).remove()

// Remove control bar
//$(theoClasses.controlBar).hide()
$(theoClasses.controlBar).css({zIndex: -100}); // we need this visible to get the width of the seekbar

$('.theo-loading-spinner-circle-left').css({color: 'red'})
$('.theo-loading-spinner-circle-right').css({color: 'green'})

function toggleFullscreen() {
    $(theoClasses.fullscreenControl).click()
}

function seekbarTouchDown(progress) {
    // get the width of the thumbRect of THEO
    var bubbleWidth = 0
    var bubble = document.querySelector(theoClasses.thumbProgress)
    if (bubble) {
        var beforeBubble = window.getComputedStyle(bubble, ':before')
        if (beforeBubble && !isNaN(parseInt(beforeBubble.width))) { bubbleWidth = parseInt(beforeBubble.width) }
    }
    
    var xCoor = $(theoClasses.progressControl).width() * progress
    $(theoClasses.seekBall).simulate('mousedown', { clientX: xCoor + bubbleWidth, clientY: 0 })
}

function seekbarTouchExit() {
    $(theoClasses.seekBall).simulate('mouseup')
}