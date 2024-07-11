use std::env::args;

use image::{ImageFormat, Rgba};

const PIXEL_SIZE: u32 = 11;

fn is_same(a: &Rgba<u8>, b: &Rgba<u8>) -> bool {
    let a = a.0;
    let b = b.0;

    let threshold: u8 = 10;
    for i in 0..a.len() {
        let diff = a[i].abs_diff(b[i]);
        if diff > threshold {
            return false;
        }
    }

    return true;
}

fn main() {
    let mut iter = args().skip(1);
    let img_file = iter.next().expect("image has to be provided");
    let use_base = iter.next().is_some_and(|e| e == "--base");

    let res = image::open(&img_file).expect("Could not load image");
    let res = res.as_rgba8().expect("could not get rgba8 image");

    let base_img = image::open("base.png").expect("Could not load base image");
    let base_img = base_img.as_rgba8().expect("could not get rgba8 image");

    let (width, height) = res.dimensions();
    let mc_width = width / PIXEL_SIZE;
    let mc_height = height / PIXEL_SIZE;

    if mc_width > base_img.width() || mc_height > base_img.height() {
        panic!("Image sizes do not match (has to be {} {} but has {} {})", base_img.width() * PIXEL_SIZE, base_img.height() * PIXEL_SIZE, width, height);
    }


    let desired_size = 55;
    let mut out_img = image::ImageBuffer::<Rgba<u8>, Vec<u8>>::new(desired_size, desired_size);

    let mc_base_x_start = base_img.width() - mc_width;
    let mc_base_y_start = base_img.height() - mc_height;

    for mc_x in 0..mc_width {
        for mc_y in 0..mc_height {
            let mut r = 0;
            let mut g = 0;
            let mut b = 0;
            let mut a = 0;
            let mut total = 0;

            for x in 0..PIXEL_SIZE {
                for y in 0..PIXEL_SIZE {
                    let p_x = mc_x * PIXEL_SIZE + x;
                    let p_y = mc_y * PIXEL_SIZE + y;

                    if p_x >= width || p_y >= height {
                        continue;
                    }

                    let pixel = res.get_pixel(p_x, p_y);
                    r += pixel[0] as u32;
                    g += pixel[1] as u32;
                    b += pixel[2] as u32;
                    a += pixel[3] as u32;
                    total += 1;
                }
            }

            r /= total;
            g /= total;
            b /= total;
            a /= total;

            let to_write = Rgba([r as u8, g as u8, b as u8, a as u8]);
            if use_base {
                let base_pixel = base_img.get_pixel(mc_x + mc_base_x_start, mc_y + mc_base_y_start);
                if is_same(&base_pixel, &to_write) {
                    continue;
                }
            }

            out_img.put_pixel(mc_x + mc_base_x_start, mc_y + mc_base_y_start, to_write);
        }
    }


    out_img.save_with_format(img_file.replace(".png", "") + "_pixel.png", ImageFormat::Png).expect("Could not save image");
}
