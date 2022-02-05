export const MeetView = requireNativeComponent('RNMeetView');
export const MeetModule = NativeModules.RNMeetModule
const call = MeetModule.call;

MeetModule.call = (options) => {
  options=options||{};
  call(options);
}

export default MeetModule;
