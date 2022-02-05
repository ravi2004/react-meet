export const MeetView = requireNativeComponent('RNMeetView');
export const MeetModule = NativeModules.RNMeetModule
const call = MeetModule.call;
const hide=MeetModule.hide;
const audioCall = MeetModule.audioCall;
const endCall=MeetModule.endCall;
MeetModule.call = (userInfo, moptions) => {
  userInfo = userInfo || {};
  moptions=moptions||{};
  call(userInfo, moptions);
}
MeetModule.hide=()=>{
  hide();
}
MeetModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
}
export default MeetModule;
