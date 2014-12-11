/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Studies\\Android3\\POSA-14-master_10\\POSA-14-master\\assignments\\week-8-assignment-7\\W8-A7-ThreadedDownloads-BoundServices\\src\\edu\\vuum\\mocca\\DownloadRequest.aidl
 */
package edu.vuum.mocca;
/**
 * @class DownloadRequest
 *
 * @brief An AIDL interface for downloading an image from another
 *        process. The caller should provide a DownloadCallback object
 *        so that the downloading process can return a result across
 *        process boundaries asynchronously.
 *
 *		This file generates a java interface in /gen
 */
public interface DownloadRequest extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.vuum.mocca.DownloadRequest
{
private static final java.lang.String DESCRIPTOR = "edu.vuum.mocca.DownloadRequest";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.vuum.mocca.DownloadRequest interface,
 * generating a proxy if needed.
 */
public static edu.vuum.mocca.DownloadRequest asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.vuum.mocca.DownloadRequest))) {
return ((edu.vuum.mocca.DownloadRequest)iin);
}
return new edu.vuum.mocca.DownloadRequest.Stub.Proxy(obj);
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
edu.vuum.mocca.DownloadCallback _arg1;
_arg1 = edu.vuum.mocca.DownloadCallback.Stub.asInterface(data.readStrongBinder());
this.downloadImage(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.vuum.mocca.DownloadRequest
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
// Download an image at the given uri and store it on the file system.
// When finished, call the appropriate method on the callback object to
// send the downloaded file's file name on the file system.

@Override public void downloadImage(android.net.Uri uri, edu.vuum.mocca.DownloadCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((uri!=null)) {
_data.writeInt(1);
uri.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_downloadImage, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_downloadImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
// Download an image at the given uri and store it on the file system.
// When finished, call the appropriate method on the callback object to
// send the downloaded file's file name on the file system.

public void downloadImage(android.net.Uri uri, edu.vuum.mocca.DownloadCallback callback) throws android.os.RemoteException;
}
