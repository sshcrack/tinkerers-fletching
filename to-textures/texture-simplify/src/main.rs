use std::{collections::HashMap, env::args, path::PathBuf, str::FromStr};

use image::{GenericImageView, Rgba};

fn is_same_color(a: Rgba<u8>, b: Rgba<u8>, tolerance: f32) -> bool {
    let a = a.0;
    let b = b.0;

    let dr = a[0] as f32 - b[0] as f32;
    let dg = a[1] as f32 - b[1] as f32;
    let db = a[2] as f32 - b[2] as f32;
    let da = a[3] as f32 - b[3] as f32;

    dr * dr + dg * dg + db * db + da * da < tolerance * tolerance
}

fn main() {
    let tolerance = 0.1;

    let in_path = args().nth(1).expect("First argument must be the input path");
    let out_path = args().nth(2).expect("Second argument must be the output path");

    let input = image::open(PathBuf::from_str(&in_path).expect("Invalid input path")).unwrap();
    let out_path = PathBuf::from_str(&out_path).expect("Invalid output path");

    let colors = HashMap::new();

    let mut output = image::DynamicImage::new_rgba8(input.width(), input.height());
    for (x, y, pixel) in input.pixels() {
        let v = colors.values();
        for (color, _) in v {
            if is_same_color(*color, pixel, tolerance) {
                output.put_pixel(x, y, *color);
                break;
            }
        }
    }
}
