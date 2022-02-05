import { NativeModules, requireNativeComponent } from 'react-native';

export const MeetView = requireNativeComponent('RNMeetView');
export const MeetModule = NativeModules.RNMeetModule
const call = MeetModule.call;
const hide=MeetModule.hide;

MeetModule.call = (options) => {
  options=options||{};
  call(options);
}

export default MeetModule;
