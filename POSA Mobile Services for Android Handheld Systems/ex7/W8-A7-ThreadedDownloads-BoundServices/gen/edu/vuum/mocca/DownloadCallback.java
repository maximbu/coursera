/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Studies\\Android3\\POSA-14-master_10\\POSA-14-master\\assignments\\week-8-assignment-7\\W8-A7-ThreadedDownloads-BoundServices\\src\\edu\\vuum\\mocca\\DownloadCallback.aidl
 */
package edu.vuum.mocca;
/**
 * @class
 *
 * @brief An AIDL Interface used for receiving results from a call to
 *        DownloadRequest.downloadImage()
 *
 *		This file generates a java interface in /gen
 */
public interface DownloadCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements edu.vuum.mocca.DownloadCallback
{
private static final java.lang.String DESCRIPTOR = "edu.vuum.mocca.DownloadCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an edu.vuum.mocca.DownloadCallback interface,
 * generating a proxy if needed.
 */
public static edu.vuum.mocca.DownloadCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof edu.vuum.mocca.DownloadCallback))) {
return ((edu.vuum.mocca.DownloadCallback)iin);
}
return new edu.vuum.mocca.DownloadCallback.Stub.Proxy(obj);
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
case TRANSACTION_sendPath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendPath(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements edu.vuum.mocca.DownloadCallback
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
// Send the location of a downloaded file on the file system back to the caller 

@Override public void sendPath(java.lang.String filePath) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(filePath);
mRemote.transact(Stub.TRANSACTION_sendPath, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_sendPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
// Send the location of a downloaded file on the file system back to the caller 

public void sendPath(java.lang.String filePath) throws android.os.RemoteException;
}
