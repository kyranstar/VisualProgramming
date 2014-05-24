/*
  * Copyright (c) 2006 Romain Guy <romain.guy@mac.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package core.graphics.imageutil;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public enum BlendComposite implements Composite {
	
		 		NORMAL(BlendingMode.NORMAL),
		 		AVERAGE(BlendingMode.AVERAGE),
		 		MULTIPLY(BlendingMode.MULTIPLY),
		 		SCREEN(BlendingMode.SCREEN),
		 		DARKEN(BlendingMode.DARKEN),
			    LIGHTEN(BlendingMode.LIGHTEN),
			    OVERLAY(BlendingMode.OVERLAY),
			    HARD_LIGHT(BlendingMode.HARD_LIGHT),
			    SOFT_LIGHT(BlendingMode.SOFT_LIGHT),
			    DIFFERENCE(BlendingMode.DIFFERENCE),
			    NEGATION(BlendingMode.NEGATION),
			    EXCLUSION(BlendingMode.EXCLUSION),
			    COLOR_DODGE(BlendingMode.COLOR_DODGE),
			    INVERSE_COLOR_DODGE(BlendingMode.INVERSE_COLOR_DODGE),
			    SOFT_DODGE(BlendingMode.SOFT_DODGE),
			    COLOR_BURN(BlendingMode.COLOR_BURN),
			    INVERSE_COLOR_BURN(BlendingMode.INVERSE_COLOR_BURN),
			    SOFT_BURN(BlendingMode.SOFT_BURN),
			    REFLECT(BlendingMode.REFLECT),
			    GLOW(BlendingMode.GLOW),
			    FREEZE(BlendingMode.FREEZE),
			    HEAT(BlendingMode.HEAT),
			    ADD(BlendingMode.ADD),
			    SUBTRACT(BlendingMode.SUBTRACT),
			    STAMP(BlendingMode.STAMP),
			    RED(BlendingMode.RED),
			    GREEN(BlendingMode.GREEN),
			    BLUE(BlendingMode.BLUE),
			    HUE(BlendingMode.HUE),
			    SATURATION(BlendingMode.SATURATION),
			    COLOR(BlendingMode.COLOR),
			    LUMINOSITY(BlendingMode.LUMINOSITY);

	
	
    private float alpha;
    private BlendingMode mode;

    private BlendComposite(final BlendingMode mode) {
        this(mode, 1.0f);
    }

    private BlendComposite(final BlendingMode mode, final float alpha) {
        this.mode = mode;
        setAlpha(alpha);
    }

    public static BlendComposite getInstance(final BlendingMode mode) {
    	for(BlendComposite bc : values()){
    		if(bc.mode == mode) {
				return bc;
			}
    	}
    	return null;
    }

    public float getAlpha() {
        return alpha;
    }

    public BlendingMode getMode() {
        return mode;
    }

    private void setAlpha(final float alpha) {
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException(
                    "alpha must be comprised between 0.0f and 1.0f");
        }
        this.alpha = alpha;
    }
    public CompositeContext createContext(final ColorModel srcColorModel,
                                          final ColorModel dstColorModel,
                                          final RenderingHints hints) {
        return new BlendingContext(this);
    }

    private static final class BlendingContext implements CompositeContext {
        private final Blender blender;
        private final BlendComposite composite;

        private BlendingContext(final BlendComposite composite) {
            this.composite = composite;
            this.blender = Blender.getBlenderFor(composite);
        }

        public void dispose() {
        }

        public void compose(final Raster src, final Raster dstIn, final WritableRaster dstOut) {
            if (src.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
                dstIn.getSampleModel().getDataType() != DataBuffer.TYPE_INT ||
                dstOut.getSampleModel().getDataType() != DataBuffer.TYPE_INT) {
                throw new IllegalStateException(
                        "Source and destination must store pixels as INT.");
            }

            int width = Math.min(src.getWidth(), dstIn.getWidth());
            int height = Math.min(src.getHeight(), dstIn.getHeight());

            float alpha = composite.getAlpha();

            int[] srcPixel = new int[4];
            int[] dstPixel = new int[4];
            int[] srcPixels = new int[width];
            int[] dstPixels = new int[width];

            for (int y = 0; y < height; y++) {
                src.getDataElements(0, y, width, 1, srcPixels);
                dstIn.getDataElements(0, y, width, 1, dstPixels);
                for (int x = 0; x < width; x++) {
                    // pixels are stored as INT_ARGB
                    // our arrays are [R, G, B, A]
                    int pixel = srcPixels[x];
                    srcPixel[0] = (pixel >> 16) & 0xFF;
                    srcPixel[1] = (pixel >>  8) & 0xFF;
                    srcPixel[2] = (pixel      ) & 0xFF;
                    srcPixel[3] = (pixel >> 24) & 0xFF;

                    pixel = dstPixels[x];
                    dstPixel[0] = (pixel >> 16) & 0xFF;
                    dstPixel[1] = (pixel >>  8) & 0xFF;
                    dstPixel[2] = (pixel      ) & 0xFF;
                    dstPixel[3] = (pixel >> 24) & 0xFF;

                    int[] result = blender.blend(srcPixel, dstPixel);

                    // mixes the result with the opacity
                    dstPixels[x] = ((int) (dstPixel[3] + (result[3] - dstPixel[3]) * alpha) & 0xFF) << 24 |
                                   ((int) (dstPixel[0] + (result[0] - dstPixel[0]) * alpha) & 0xFF) << 16 |
                                   ((int) (dstPixel[1] + (result[1] - dstPixel[1]) * alpha) & 0xFF) <<  8 |
                                    (int) (dstPixel[2] + (result[2] - dstPixel[2]) * alpha) & 0xFF;
                }
                dstOut.setDataElements(0, y, width, 1, dstPixels);
            }
        }
    }

    public enum BlendingMode {
        NORMAL,
        AVERAGE,
        MULTIPLY,
        SCREEN,
        DARKEN,
        LIGHTEN,
        OVERLAY,
        HARD_LIGHT,
        SOFT_LIGHT,
        DIFFERENCE,
        NEGATION,
        EXCLUSION,
        COLOR_DODGE,
        INVERSE_COLOR_DODGE,
        SOFT_DODGE,
        COLOR_BURN,
        INVERSE_COLOR_BURN,
        SOFT_BURN,
        REFLECT,
        GLOW,
        FREEZE,
        HEAT,
        ADD,
        SUBTRACT,
        STAMP,
        RED,
        GREEN,
        BLUE,
        HUE,
        SATURATION,
        COLOR,
        LUMINOSITY
    }

  

    private abstract static class Blender {
        public abstract int[] blend(int[] src, int[] dst);

        private static void convertRGBtoHSL(final int r, final int g, final int b, final float[] hsl) {
            float varR = (r / 255f);
            float varG = (g / 255f);
            float varB = (b / 255f);

            float varMin;
            float varMax;
            float delMax;

            if (varR > varG) {
                varMin = varG;
                varMax = varR;
            } else {
                varMin = varR;
                varMax = varG;
            }
            if (varB > varMax) {
                varMax = varB;
            }
            if (varB < varMin) {
                varMin = varB;
            }

            delMax = varMax - varMin;

            float hue;
            float saturation;
            float lightness;
            lightness = (varMax + varMin) / 2f;

            if (delMax - 0.01f <= 0.0f) {
                hue = 0;
                saturation = 0;
            } else {
                if (lightness < 0.5f) {
                    saturation = delMax / (varMax + varMin);
                } else {
                    saturation = delMax / (2 - varMax - varMin);
                }

                float delRed = (((varMax - varR) / 6f) + (delMax / 2f)) / delMax;
                float delGreen = (((varMax - varG) / 6f) + (delMax / 2f)) / delMax;
                float delBlue = (((varMax - varB) / 6f) + (delMax / 2f)) / delMax;

                if (varR == varMax) {
                    hue = delBlue - delGreen;
                } else if (varG == varMax) {
                    hue = (1 / 3f) + delRed - delBlue;
                } else {
                    hue = (2 / 3f) + delGreen - delRed;
                }
                if (hue < 0) {
                    hue += 1;
                }
                if (hue > 1) {
                    hue -= 1;
                }
            }

            hsl[0] = hue;
            hsl[1] = saturation;
            hsl[2] = lightness;
        }

        private static void convertHSLtoRGB(final float h, final float s, final float l, final int[] rgb) {
            int red;
            int green;
            int blue;

            if (s - 0.01f <= 0.0f) {
                red = (int) (l * 255.0f);
                green = (int) (l * 255.0f);
                blue = (int) (l * 255.0f);
            } else {
                float var1;
                float var2;
                if (l < 0.5f) {
                    var2 = l * (1 + s);
                } else {
                    var2 = (l + s) - (s * l);
                }
                var1 = 2 * l - var2;

                red = (int) (255.0f * hue2RGB(var1, var2, h + (1.0f / 3.0f)));
                green = (int) (255.0f * hue2RGB(var1, var2, h));
                blue = (int) (255.0f * hue2RGB(var1, var2, h - (1.0f / 3.0f)));
            }

            rgb[0] = red;
            rgb[1] = green;
            rgb[2] = blue;
        }

        private static float hue2RGB(final float v1, final float v2, float vH) {
            if (vH < 0.0f) {
                vH += 1.0f;
            }
            if (vH > 1.0f) {
                vH -= 1.0f;
            }
            if ((6.0f * vH) < 1.0f) {
                return (v1 + (v2 - v1) * 6.0f * vH);
            }
            if ((2.0f * vH) < 1.0f) {
                return (v2);
            }
            if ((3.0f * vH) < 2.0f) {
                return (v1 + (v2 - v1) * ((2.0f / 3.0f) - vH) * 6.0f);
            }
            return (v1);
        }

        public static Blender getBlenderFor(final BlendComposite composite) {
            switch (composite.getMode()) {
                case NORMAL:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return src;
                        }
                    };
                case ADD:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.min(255, src[0] + dst[0]),
                                Math.min(255, src[1] + dst[1]),
                                Math.min(255, src[2] + dst[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case AVERAGE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                (src[0] + dst[0]) >> 1,
                                (src[1] + dst[1]) >> 1,
                                (src[2] + dst[2]) >> 1,
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case BLUE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0],
                                src[1],
                                dst[2],
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case COLOR:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            float[] srcHSL = new float[3];
                            convertRGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            convertRGBtoHSL(dst[0], dst[1], dst[2], dstHSL);

                            int[] result = new int[4];
                            convertHSLtoRGB(srcHSL[0], srcHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3]);

                            return result;
                        }
                    };
                case COLOR_BURN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[0]) << 8) / src[0])),
                                src[1] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[1]) << 8) / src[1])),
                                src[2] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - dst[2]) << 8) / src[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case COLOR_DODGE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0] == 255 ? 255 :
                                    Math.min((dst[0] << 8) / (255 - src[0]), 255),
                                src[1] == 255 ? 255 :
                                    Math.min((dst[1] << 8) / (255 - src[1]), 255),
                                src[2] == 255 ? 255 :
                                    Math.min((dst[2] << 8) / (255 - src[2]), 255),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case DARKEN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.min(src[0], dst[0]),
                                Math.min(src[1], dst[1]),
                                Math.min(src[2], dst[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case DIFFERENCE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.abs(dst[0] - src[0]),
                                Math.abs(dst[1] - src[1]),
                                Math.abs(dst[2] - src[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case EXCLUSION:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] + src[0] - (dst[0] * src[0] >> 7),
                                dst[1] + src[1] - (dst[1] * src[1] >> 7),
                                dst[2] + src[2] - (dst[2] * src[2] >> 7),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case FREEZE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0] == 0 ? 0 : Math.max(0, 255 - (255 - dst[0]) * (255 - dst[0]) / src[0]),
                                src[1] == 0 ? 0 : Math.max(0, 255 - (255 - dst[1]) * (255 - dst[1]) / src[1]),
                                src[2] == 0 ? 0 : Math.max(0, 255 - (255 - dst[2]) * (255 - dst[2]) / src[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case GLOW:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] == 255 ? 255 : Math.min(255, src[0] * src[0] / (255 - dst[0])),
                                dst[1] == 255 ? 255 : Math.min(255, src[1] * src[1] / (255 - dst[1])),
                                dst[2] == 255 ? 255 : Math.min(255, src[2] * src[2] / (255 - dst[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case GREEN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0],
                                dst[1],
                                src[2],
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case HARD_LIGHT:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0] < 128 ? dst[0] * src[0] >> 7 :
                                    255 - ((255 - src[0]) * (255 - dst[0]) >> 7),
                                src[1] < 128 ? dst[1] * src[1] >> 7 :
                                    255 - ((255 - src[1]) * (255 - dst[1]) >> 7),
                                src[2] < 128 ? dst[2] * src[2] >> 7 :
                                    255 - ((255 - src[2]) * (255 - dst[2]) >> 7),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case HEAT:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] == 0 ? 0 : Math.max(0, 255 - (255 - src[0]) * (255 - src[0]) / dst[0]),
                                dst[1] == 0 ? 0 : Math.max(0, 255 - (255 - src[1]) * (255 - src[1]) / dst[1]),
                                dst[2] == 0 ? 0 : Math.max(0, 255 - (255 - src[2]) * (255 - src[2]) / dst[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case HUE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            float[] srcHSL = new float[3];
                            convertRGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            convertRGBtoHSL(dst[0], dst[1], dst[2], dstHSL);

                            int[] result = new int[4];
                            convertHSLtoRGB(srcHSL[0], dstHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3]);

                            return result;
                        }
                    };
                case INVERSE_COLOR_BURN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[0]) << 8) / dst[0])),
                                dst[1] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[1]) << 8) / dst[1])),
                                dst[2] == 0 ? 0 :
                                    Math.max(0, 255 - (((255 - src[2]) << 8) / dst[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case INVERSE_COLOR_DODGE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] == 255 ? 255 :
                                    Math.min((src[0] << 8) / (255 - dst[0]), 255),
                                dst[1] == 255 ? 255 :
                                    Math.min((src[1] << 8) / (255 - dst[1]), 255),
                                dst[2] == 255 ? 255 :
                                    Math.min((src[2] << 8) / (255 - dst[2]), 255),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case LIGHTEN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.max(src[0], dst[0]),
                                Math.max(src[1], dst[1]),
                                Math.max(src[2], dst[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case LUMINOSITY:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            float[] srcHSL = new float[3];
                            convertRGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            convertRGBtoHSL(dst[0], dst[1], dst[2], dstHSL);

                            int[] result = new int[4];
                            convertHSLtoRGB(dstHSL[0], dstHSL[1], srcHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3]);

                            return result;
                        }
                    };
                case MULTIPLY:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                (src[0] * dst[0]) >> 8,
                                (src[1] * dst[1]) >> 8,
                                (src[2] * dst[2]) >> 8,
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case NEGATION:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                255 - Math.abs(255 - dst[0] - src[0]),
                                255 - Math.abs(255 - dst[1] - src[1]),
                                255 - Math.abs(255 - dst[2] - src[2]),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case OVERLAY:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] < 128 ? dst[0] * src[0] >> 7 :
                                    255 - ((255 - dst[0]) * (255 - src[0]) >> 7),
                                dst[1] < 128 ? dst[1] * src[1] >> 7 :
                                    255 - ((255 - dst[1]) * (255 - src[1]) >> 7),
                                dst[2] < 128 ? dst[2] * src[2] >> 7 :
                                    255 - ((255 - dst[2]) * (255 - src[2]) >> 7),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case RED:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0],
                                dst[1],
                                dst[2],
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case REFLECT:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                src[0] == 255 ? 255 : Math.min(255, dst[0] * dst[0] / (255 - src[0])),
                                src[1] == 255 ? 255 : Math.min(255, dst[1] * dst[1] / (255 - src[1])),
                                src[2] == 255 ? 255 : Math.min(255, dst[2] * dst[2] / (255 - src[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case SATURATION:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            float[] srcHSL = new float[3];
                            convertRGBtoHSL(src[0], src[1], src[2], srcHSL);
                            float[] dstHSL = new float[3];
                            convertRGBtoHSL(dst[0], dst[1], dst[2], dstHSL);

                            int[] result = new int[4];
                            convertHSLtoRGB(dstHSL[0], srcHSL[1], dstHSL[2], result);
                            result[3] = Math.min(255, src[3] + dst[3]);

                            return result;
                        }
                    };
                case SCREEN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                255 - ((255 - src[0]) * (255 - dst[0]) >> 8),
                                255 - ((255 - src[1]) * (255 - dst[1]) >> 8),
                                255 - ((255 - src[2]) * (255 - dst[2]) >> 8),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case SOFT_BURN:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] + src[0] < 256 ?
	                                (dst[0] == 255 ? 255 :
                                        Math.min(255, (src[0] << 7) / (255 - dst[0]))) :
                                            Math.max(0, 255 - (((255 - dst[0]) << 7) / src[0])),
                                dst[1] + src[1] < 256 ?
	                                (dst[1] == 255 ? 255 :
                                        Math.min(255, (src[1] << 7) / (255 - dst[1]))) :
                                            Math.max(0, 255 - (((255 - dst[1]) << 7) / src[1])),
                                dst[2] + src[2] < 256 ?
	                                (dst[2] == 255 ? 255 :
                                        Math.min(255, (src[2] << 7) / (255 - dst[2]))) :
                                            Math.max(0, 255 - (((255 - dst[2]) << 7) / src[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case SOFT_DODGE:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                dst[0] + src[0] < 256 ?
                                    (src[0] == 255 ? 255 :
                                        Math.min(255, (dst[0] << 7) / (255 - src[0]))) :
                                            Math.max(0, 255 - (((255 - src[0]) << 7) / dst[0])),
                                dst[1] + src[1] < 256 ?
                                    (src[1] == 255 ? 255 :
                                        Math.min(255, (dst[1] << 7) / (255 - src[1]))) :
                                            Math.max(0, 255 - (((255 - src[1]) << 7) / dst[1])),
                                dst[2] + src[2] < 256 ?
                                    (src[2] == 255 ? 255 :
                                        Math.min(255, (dst[2] << 7) / (255 - src[2]))) :
                                            Math.max(0, 255 - (((255 - src[2]) << 7) / dst[2])),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case SOFT_LIGHT:
                    break;
                case STAMP:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.max(0, Math.min(255, dst[0] + 2 * src[0] - 256)),
                                Math.max(0, Math.min(255, dst[1] + 2 * src[1] - 256)),
                                Math.max(0, Math.min(255, dst[2] + 2 * src[2] - 256)),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
                case SUBTRACT:
                    return new Blender() {
                        @Override
                        public int[] blend(final int[] src, final int[] dst) {
                            return new int[] {
                                Math.max(0, src[0] + dst[0] - 256),
                                Math.max(0, src[1] + dst[1] - 256),
                                Math.max(0, src[2] + dst[2] - 256),
                                Math.min(255, src[3] + dst[3])
                            };
                        }
                    };
			default:
				break;
            }
            throw new IllegalArgumentException("Blender not implemented for " +
                                               composite.getMode().name());
        }
    }
}
