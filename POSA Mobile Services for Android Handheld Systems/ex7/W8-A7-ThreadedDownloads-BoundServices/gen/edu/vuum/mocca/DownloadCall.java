/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Studies\\Android3\\POSA-14-master_10\\POSA-14-master\\assignments\\week-8-assignment-7\\W8-A7-ThreadedDownloads-BoundServices\\src\\edu\\vuum\\mocca\\DownloadCall.aidl
 */
package edu.vuum.mocca;
/**
 * @class DownloadCall
 *
 * @brief An AIDL interface used to download an image in another
 *        process. Any invocations of downloadImage() will block until
 *        the function completes and returns from the other process.
 *
 *		This file generates a java interface in /gen
 */
public interface DownloadCall extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.vuum.mocca.DownloadCall
{
private static final java.lang.String DESCRIPTOR = "edu.vuum.mocca.DownloadCall";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.vuum.mocca.DownloadCall interface,
 * generating a proxy if needed.
 */
public static edu.vuum.mocca.DownloadCall asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.vuum.mocca.DownloadCall))) {
return ((edu.vuum.mocca.DownloadCall)iin);
}
return new edu.vuum.mocca.DownloadCall.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_downloadImage:
{
data.enforceInterface(DESCRIPTOR);
android.net.Uri _arg0;
if ((0!=data.readInt())) {
_arg0 = android.net.Uri.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _result = this.downloadImage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.vuum.mocca.DownloadCall
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
// Download the image at the given web uri then return a string to its location on the file system.

@Override public java.lang.String downloadImage(android.net.Uri uri) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((uri!=null)) {
_data.writeInt(1);
uri.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_downloadImage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_downloadImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
// Download the image at the given web uri then return a string to its location on the file system.

public java.lang.String downloadImage(android.net.Uri uri) throws android.os.RemoteException;
}
