package com.agribuddy.staff.utils.base;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by nguyenkhacphilong on 11/2/15.
 */
public class TakePhotoUtils {

    public static Uri getOutputMediaFileUri() {
        File folder = new File(Environment.getExternalStorageDirectory()
                + File.separator + "AgribuddyStaff" + File.separator
                + "Photos");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File mFile = new File(folder.getPath() + File.separator + "IMG_"
                + Utils.getTimeStamp() + ".jpg");
        return Uri.fromFile(mFile);
    }


    // Reduce file size
    public static void resizePhotoWithPath(Context context, Uri imgUri, int reqWidth,
                                           int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(getFile(context, imgUri).getAbsolutePath(), options);
        options.inDither = false; // Disable Dithering mode
        options.inPurgeable = true; // Tell to gc that whether it needs free
        options.inInputShareable = true; // Which kind of reference will be
        options.inTempStorage = new byte[32 * 1024];
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        options.inJustDecodeBounds = false;

        Bitmap bMapResize = BitmapFactory.decodeFile(getFile(context, imgUri).getAbsolutePath(), options);
        int mOrientation = 0;
        try {
            ExifInterface ei = new ExifInterface(getFile(context, imgUri).getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mOrientation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mOrientation = 180;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    mOrientation = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    mOrientation = 270;
                    break;
            }
        } catch (Exception e) {
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(mOrientation);

        try {
            FileOutputStream fos = new FileOutputStream(getFile(context, imgUri).getAbsolutePath());

            Bitmap bMapRotate = Bitmap.createBitmap(bMapResize, 0, 0,
                    bMapResize.getWidth(), bMapResize.getHeight(), matrix, true);
            bMapRotate.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            fos.flush();
            fos.close();

            bMapRotate.recycle();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createFileFromBitmap(Bitmap bitmap) {
        Uri mUri = getOutputMediaFileUri();
        try {
            FileOutputStream fos = new FileOutputStream(mUri.getPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mUri.getPath();
    }

    // Reduce file size
    public static Uri copyAndResizePhotoUri(Context context, Uri imgUri, int reqWidth,
                                            int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(getFile(context, imgUri).getAbsolutePath(), options);
        options.inDither = false; // Disable Dithering mode
        options.inPurgeable = true; // Tell to gc that whether it needs free
        options.inInputShareable = true; // Which kind of reference will be
        options.inTempStorage = new byte[32 * 1024];
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        options.inJustDecodeBounds = false;

        Bitmap bMapResize = BitmapFactory.decodeFile(getFile(context, imgUri).getAbsolutePath(), options);
        int mOrientation = 0;
        try {
            ExifInterface ei = new ExifInterface(getFile(context, imgUri).getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mOrientation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mOrientation = 180;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    mOrientation = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    mOrientation = 270;
                    break;
            }
        } catch (Exception e) {
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(mOrientation);

        File outputFile = new File(getUnixFilePath(context, ".jpg"));

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);

            Bitmap bMapRotate = Bitmap.createBitmap(bMapResize, 0, 0,
                    bMapResize.getWidth(), bMapResize.getHeight(), matrix, true);
            bMapRotate.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            fos.flush();
            fos.close();

            bMapRotate.recycle();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(outputFile);
    }


    public static String getUnixFilePath(Context mContext, String fileExtension) {

        File tempFolder = new File(mContext.getExternalCacheDir().getAbsolutePath() + "/upload");
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        String filePath = tempFolder.getPath() + "/" + Utils.getTimeStamp() + fileExtension;

        return filePath;
    }

    public static void deleteTempUploadCache(Context mContext) {
        if (!((Activity) mContext).isFinishing()) {
            if (mContext.getExternalCacheDir() != null) {
                File dir = new File(mContext.getExternalCacheDir().getAbsolutePath() + "/upload");
                if (dir.isDirectory()) {
                    for (File child : dir.listFiles()) {
                        child.delete();
                    }
                }
            }
        }
    }

    // tinh toan kich thuoc file
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        while ((height / inSampleSize) > reqHeight
                && (width / inSampleSize) > reqWidth) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static File getFile(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            File f = new File(context.getExternalCacheDir(), getPath(context, uri));
            try {
                InputStream is = context.getContentResolver().openInputStream(uri);
                OutputStream os = new FileOutputStream(f);
                Utils.CopyStream(is, os);
                os.close();
                return f;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return new File(uri.getPath());
        }

        return null;
    }

    public static String getPath(final Context context, final Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String[] projection = {MediaStore.Images.Media.DATA, MediaStore.MediaColumns.DISPLAY_NAME};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static boolean actualFileExists(Context context, Uri uri) {
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Bitmap resizeBitmap(Bitmap b, int size) {
        int width = b.getWidth();
        int height = b.getHeight();
        if (width > height) {
            // landscape
            float ratio = (float) width / size;
            width = size;
            height = (int) (height / ratio);
        } else if (height > width) {
            // portrait
            float ratio = (float) height / size;
            height = size;
            width = (int) (width / ratio);
        } else {
            // square
            height = size;
            width = size;
        }
        return Bitmap.createScaledBitmap(b, width, height, true);
    }
}
