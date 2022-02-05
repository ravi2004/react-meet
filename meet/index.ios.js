import { NativeModules, requireNativeComponent } from 'react-native';

export const MeetView = requireNativeComponent('RNMeetView');
export const MeetModule = NativeModules.RNMeetModule;
const call = MeetModule.call;

MeetModule.call = (moptions) => {
  moptions=moptions||{};
  call(moptions);
}

export default JitsiMeetModule;


