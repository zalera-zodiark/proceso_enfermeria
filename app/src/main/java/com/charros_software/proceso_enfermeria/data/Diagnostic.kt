package com.charros_software.proceso_enfermeria.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.charros_software.proceso_enfermeria.R
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D1
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D11
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D12
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D2
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D3
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D4
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D5
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D6
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D7
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass1D9
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D1
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D10
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D11
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D12
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D13
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D3
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D4
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D6
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D7
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D8
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass2D9
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D10
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D11
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D12
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D4
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D6
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D7
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D8
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass3D9
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass4D11
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass4D2
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass4D3
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass4D4
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass4D5
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass5D11
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass5D2
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass5D4
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass5D5
import com.charros_software.proceso_enfermeria.data.NANDAClass.Klass6D11
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain1
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain10
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain11
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain12
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain13
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain2
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain3
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain4
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain5
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain6
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain7
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain8
import com.charros_software.proceso_enfermeria.data.NANDADomain.Domain9
import com.charros_software.proceso_enfermeria.domain.model.DiagnosticModel

data class Diagnostic(
    val domain: NANDADomain,
    val klass: NANDAClass,
    val name: Int
)

@Composable
fun Diagnostic.toDomain(): DiagnosticModel {
    val (number, diagnostic) = stringResource(id = name).split("|")
    return DiagnosticModel(
        stringResource(id = domain.description),
        stringResource(id = klass.description),
        number.toInt(), diagnostic)
}

val nandaList = listOf(
    Diagnostic(Domain1, Klass1D1, R.string._00097),
    Diagnostic(Domain1, Klass1D1, R.string._00262),
    Diagnostic(Domain1, Klass1D1, R.string._00168),
    Diagnostic(Domain1, Klass2D1, R.string._00290),
    Diagnostic(Domain1, Klass2D1, R.string._00257),
    Diagnostic(Domain1, Klass2D1, R.string._00231),
    Diagnostic(Domain1, Klass2D1, R.string._00307),
    Diagnostic(Domain1, Klass2D1, R.string._00215),
    Diagnostic(Domain1, Klass2D1, R.string._00188),
    Diagnostic(Domain1, Klass2D1, R.string._00292),
    Diagnostic(Domain1, Klass2D1, R.string._00276),
    Diagnostic(Domain1, Klass2D1, R.string._00293),
    Diagnostic(Domain1, Klass2D1, R.string._00294),
    Diagnostic(Domain1, Klass2D1, R.string._00300),
    Diagnostic(Domain1, Klass2D1, R.string._00308),
    Diagnostic(Domain1, Klass2D1, R.string._00309),
    Diagnostic(Domain1, Klass2D1, R.string._00043),

    Diagnostic(Domain2, Klass1D2, R.string._00002),
    Diagnostic(Domain2, Klass1D2, R.string._00163),
    Diagnostic(Domain2, Klass1D2, R.string._00216),
    Diagnostic(Domain2, Klass1D2, R.string._00104),
    Diagnostic(Domain2, Klass1D2, R.string._00105),
    Diagnostic(Domain2, Klass1D2, R.string._00106),
    Diagnostic(Domain2, Klass1D2, R.string._00269),
    Diagnostic(Domain2, Klass1D2, R.string._00270),
    Diagnostic(Domain2, Klass1D2, R.string._00271),
    Diagnostic(Domain2, Klass1D2, R.string._00232),
    Diagnostic(Domain2, Klass1D2, R.string._00233),
    Diagnostic(Domain2, Klass1D2, R.string._00234),
    Diagnostic(Domain2, Klass1D2, R.string._00295),
    Diagnostic(Domain2, Klass1D2, R.string._00103),
    Diagnostic(Domain2, Klass4D2, R.string._00179),
    Diagnostic(Domain2, Klass4D2, R.string._00194),
    Diagnostic(Domain2, Klass4D2, R.string._00230),
    Diagnostic(Domain2, Klass4D2, R.string._00178),
    Diagnostic(Domain2, Klass4D2, R.string._00296),
    Diagnostic(Domain2, Klass5D2, R.string._00195),
    Diagnostic(Domain2, Klass5D2, R.string._00025),
    Diagnostic(Domain2, Klass5D2, R.string._00027),
    Diagnostic(Domain2, Klass5D2, R.string._00028),
    Diagnostic(Domain2, Klass5D2, R.string._00026),

    Diagnostic(Domain3, Klass1D3, R.string._00297),
    Diagnostic(Domain3, Klass1D3, R.string._00016),
    Diagnostic(Domain3, Klass1D3, R.string._00310),
    Diagnostic(Domain3, Klass1D3, R.string._00017),
    Diagnostic(Domain3, Klass1D3, R.string._00019),
    Diagnostic(Domain3, Klass1D3, R.string._00022),
    Diagnostic(Domain3, Klass1D3, R.string._00023),
    Diagnostic(Domain3, Klass1D3, R.string._00322),
    Diagnostic(Domain3, Klass2D3, R.string._00011),
    Diagnostic(Domain3, Klass2D3, R.string._00015),
    Diagnostic(Domain3, Klass2D3, R.string._00012),
    Diagnostic(Domain3, Klass2D3, R.string._00235),
    Diagnostic(Domain3, Klass2D3, R.string._00236),
    Diagnostic(Domain3, Klass2D3, R.string._00319),
    Diagnostic(Domain3, Klass2D3, R.string._00013),
    Diagnostic(Domain3, Klass2D3, R.string._00196),
    Diagnostic(Domain3, Klass2D3, R.string._00197),
    Diagnostic(Domain3, Klass4D3, R.string._00030),

    Diagnostic(Domain4, Klass1D4, R.string._00095),
    Diagnostic(Domain4, Klass1D4, R.string._00096),
    Diagnostic(Domain4, Klass1D4, R.string._00165),
    Diagnostic(Domain4, Klass1D4, R.string._00198),
    Diagnostic(Domain4, Klass2D4, R.string._00298),
    Diagnostic(Domain4, Klass2D4, R.string._00299),
    Diagnostic(Domain4, Klass2D4, R.string._00040),
    Diagnostic(Domain4, Klass2D4, R.string._00091),
    Diagnostic(Domain4, Klass2D4, R.string._00085),
    Diagnostic(Domain4, Klass2D4, R.string._00089),
    Diagnostic(Domain4, Klass2D4, R.string._00237),
    Diagnostic(Domain4, Klass2D4, R.string._00238),
    Diagnostic(Domain4, Klass2D4, R.string._00090),
    Diagnostic(Domain4, Klass2D4, R.string._00088),
    Diagnostic(Domain4, Klass3D4, R.string._00273),
    Diagnostic(Domain4, Klass3D4, R.string._00093),
    Diagnostic(Domain4, Klass3D4, R.string._00154),
    Diagnostic(Domain4, Klass4D4, R.string._00032),
    Diagnostic(Domain4, Klass4D4, R.string._00029),
    Diagnostic(Domain4, Klass4D4, R.string._00240),
    Diagnostic(Domain4, Klass4D4, R.string._00311),
    Diagnostic(Domain4, Klass4D4, R.string._00278),
    Diagnostic(Domain4, Klass4D4, R.string._00281),
    Diagnostic(Domain4, Klass4D4, R.string._00033),
    Diagnostic(Domain4, Klass4D4, R.string._00267),
    Diagnostic(Domain4, Klass4D4, R.string._00291),
    Diagnostic(Domain4, Klass4D4, R.string._00200),
    Diagnostic(Domain4, Klass4D4, R.string._00201),
    Diagnostic(Domain4, Klass4D4, R.string._00204),
    Diagnostic(Domain4, Klass4D4, R.string._00228),
    Diagnostic(Domain4, Klass4D4, R.string._00034),
    Diagnostic(Domain4, Klass4D4, R.string._00318),
    Diagnostic(Domain4, Klass5D4, R.string._00108),
    Diagnostic(Domain4, Klass5D4, R.string._00109),
    Diagnostic(Domain4, Klass5D4, R.string._00102),
    Diagnostic(Domain4, Klass5D4, R.string._00110),
    Diagnostic(Domain4, Klass5D4, R.string._00182),
    Diagnostic(Domain4, Klass5D4, R.string._00193),

    Diagnostic(Domain5, Klass1D5, R.string._00123),
    Diagnostic(Domain5, Klass4D5, R.string._00128),
    Diagnostic(Domain5, Klass4D5, R.string._00173),
    Diagnostic(Domain5, Klass4D5, R.string._00129),
    Diagnostic(Domain5, Klass4D5, R.string._00251),
    Diagnostic(Domain5, Klass4D5, R.string._00222),
    Diagnostic(Domain5, Klass4D5, R.string._00126),
    Diagnostic(Domain5, Klass4D5, R.string._00161),
    Diagnostic(Domain5, Klass4D5, R.string._00131),
    Diagnostic(Domain5, Klass4D5, R.string._00279),
    Diagnostic(Domain5, Klass5D5, R.string._00157),
    Diagnostic(Domain5, Klass5D5, R.string._00051),

    Diagnostic(Domain6, Klass1D6, R.string._00124),
    Diagnostic(Domain6, Klass1D6, R.string._00185),
    Diagnostic(Domain6, Klass1D6, R.string._00174),
    Diagnostic(Domain6, Klass1D6, R.string._00121),
    Diagnostic(Domain6, Klass1D6, R.string._00225),
    Diagnostic(Domain6, Klass1D6, R.string._00167),
    Diagnostic(Domain6, Klass2D6, R.string._00119),
    Diagnostic(Domain6, Klass2D6, R.string._00224),
    Diagnostic(Domain6, Klass2D6, R.string._00120),
    Diagnostic(Domain6, Klass2D6, R.string._00153),
    Diagnostic(Domain6, Klass3D6, R.string._00118),

    Diagnostic(Domain7, Klass1D7, R.string._00056),
    Diagnostic(Domain7, Klass1D7, R.string._00057),
    Diagnostic(Domain7, Klass1D7, R.string._00164),
    Diagnostic(Domain7, Klass1D7, R.string._00061),
    Diagnostic(Domain7, Klass1D7, R.string._00062),
    Diagnostic(Domain7, Klass2D7, R.string._00058),
    Diagnostic(Domain7, Klass2D7, R.string._00283),
    Diagnostic(Domain7, Klass2D7, R.string._00284),
    Diagnostic(Domain7, Klass2D7, R.string._00063),
    Diagnostic(Domain7, Klass2D7, R.string._00060),
    Diagnostic(Domain7, Klass2D7, R.string._00159),
    Diagnostic(Domain7, Klass3D7, R.string._00223),
    Diagnostic(Domain7, Klass3D7, R.string._00229),
    Diagnostic(Domain7, Klass3D7, R.string._00207),
    Diagnostic(Domain7, Klass3D7, R.string._00064),
    Diagnostic(Domain7, Klass3D7, R.string._00055),
    Diagnostic(Domain7, Klass3D7, R.string._00052),

    Diagnostic(Domain8, Klass2D8, R.string._00059),
    Diagnostic(Domain8, Klass2D8, R.string._00065),
    Diagnostic(Domain8, Klass3D8, R.string._00221),
    Diagnostic(Domain8, Klass3D8, R.string._00227),
    Diagnostic(Domain8, Klass3D8, R.string._00208),
    Diagnostic(Domain8, Klass3D8, R.string._00209),

    Diagnostic(Domain9, Klass1D9, R.string._00260),
    Diagnostic(Domain9, Klass1D9, R.string._00141),
    Diagnostic(Domain9, Klass1D9, R.string._00145),
    Diagnostic(Domain9, Klass1D9, R.string._00142),
    Diagnostic(Domain9, Klass1D9, R.string._00114),
    Diagnostic(Domain9, Klass1D9, R.string._00149),
    Diagnostic(Domain9, Klass2D9, R.string._00199),
    Diagnostic(Domain9, Klass2D9, R.string._00226),
    Diagnostic(Domain9, Klass2D9, R.string._00146),
    Diagnostic(Domain9, Klass2D9, R.string._00071),
    Diagnostic(Domain9, Klass2D9, R.string._00069),
    Diagnostic(Domain9, Klass2D9, R.string._00158),
    Diagnostic(Domain9, Klass2D9, R.string._00077),
    Diagnostic(Domain9, Klass2D9, R.string._00076),
    Diagnostic(Domain9, Klass2D9, R.string._00074),
    Diagnostic(Domain9, Klass2D9, R.string._00073),
    Diagnostic(Domain9, Klass2D9, R.string._00075),
    Diagnostic(Domain9, Klass2D9, R.string._00147),
    Diagnostic(Domain9, Klass2D9, R.string._00072),
    Diagnostic(Domain9, Klass2D9, R.string._00148),
    Diagnostic(Domain9, Klass2D9, R.string._00301),
    Diagnostic(Domain9, Klass2D9, R.string._00302),
    Diagnostic(Domain9, Klass2D9, R.string._00285),
    Diagnostic(Domain9, Klass2D9, R.string._00241),
    Diagnostic(Domain9, Klass2D9, R.string._00125),
    Diagnostic(Domain9, Klass2D9, R.string._00152),
    Diagnostic(Domain9, Klass2D9, R.string._00187),
    Diagnostic(Domain9, Klass2D9, R.string._00210),
    Diagnostic(Domain9, Klass2D9, R.string._00211),
    Diagnostic(Domain9, Klass2D9, R.string._00212),
    Diagnostic(Domain9, Klass2D9, R.string._00137),
    Diagnostic(Domain9, Klass2D9, R.string._00177),
    Diagnostic(Domain9, Klass3D9, R.string._00258),
    Diagnostic(Domain9, Klass3D9, R.string._00259),
    Diagnostic(Domain9, Klass3D9, R.string._00009),
    Diagnostic(Domain9, Klass3D9, R.string._00010),
    Diagnostic(Domain9, Klass3D9, R.string._00264),
    Diagnostic(Domain9, Klass3D9, R.string._00116),
    Diagnostic(Domain9, Klass3D9, R.string._00115),
    Diagnostic(Domain9, Klass3D9, R.string._00117),

    Diagnostic(Domain10, Klass2D10, R.string._00068),
    Diagnostic(Domain10, Klass3D10, R.string._00184),
    Diagnostic(Domain10, Klass3D10, R.string._00083),
    Diagnostic(Domain10, Klass3D10, R.string._00242),
    Diagnostic(Domain10, Klass3D10, R.string._00244),
    Diagnostic(Domain10, Klass3D10, R.string._00243),
    Diagnostic(Domain10, Klass3D10, R.string._00175),
    Diagnostic(Domain10, Klass3D10, R.string._00169),
    Diagnostic(Domain10, Klass3D10, R.string._00170),
    Diagnostic(Domain10, Klass3D10, R.string._00171),
    Diagnostic(Domain10, Klass3D10, R.string._00066),
    Diagnostic(Domain10, Klass3D10, R.string._00067),

    Diagnostic(Domain11, Klass1D11, R.string._00004),
    Diagnostic(Domain11, Klass1D11, R.string._00266),
    Diagnostic(Domain11, Klass2D11, R.string._00031),
    Diagnostic(Domain11, Klass2D11, R.string._00039),
    Diagnostic(Domain11, Klass2D11, R.string._00206),
    Diagnostic(Domain11, Klass2D11, R.string._00048),
    Diagnostic(Domain11, Klass2D11, R.string._00219),
    Diagnostic(Domain11, Klass2D11, R.string._00277),
    Diagnostic(Domain11, Klass2D11, R.string._00261),
    Diagnostic(Domain11, Klass2D11, R.string._00303),
    Diagnostic(Domain11, Klass2D11, R.string._00306),
    Diagnostic(Domain11, Klass2D11, R.string._00035),
    Diagnostic(Domain11, Klass2D11, R.string._00245),
    Diagnostic(Domain11, Klass2D11, R.string._00320),
    Diagnostic(Domain11, Klass2D11, R.string._00321),
    Diagnostic(Domain11, Klass2D11, R.string._00250),
    Diagnostic(Domain11, Klass2D11, R.string._00087),
    Diagnostic(Domain11, Klass2D11, R.string._00220),
    Diagnostic(Domain11, Klass2D11, R.string._00045),
    Diagnostic(Domain11, Klass2D11, R.string._00247),
    Diagnostic(Domain11, Klass2D11, R.string._00086),
    Diagnostic(Domain11, Klass2D11, R.string._00038),
    Diagnostic(Domain11, Klass2D11, R.string._00213),
    Diagnostic(Domain11, Klass2D11, R.string._00312),
    Diagnostic(Domain11, Klass2D11, R.string._00304),
    Diagnostic(Domain11, Klass2D11, R.string._00313),
    Diagnostic(Domain11, Klass2D11, R.string._00286),
    Diagnostic(Domain11, Klass2D11, R.string._00287),
    Diagnostic(Domain11, Klass2D11, R.string._00288),
    Diagnostic(Domain11, Klass2D11, R.string._00205),
    Diagnostic(Domain11, Klass2D11, R.string._00046),
    Diagnostic(Domain11, Klass2D11, R.string._00047),
    Diagnostic(Domain11, Klass2D11, R.string._00156),
    Diagnostic(Domain11, Klass2D11, R.string._00036),
    Diagnostic(Domain11, Klass2D11, R.string._00100),
    Diagnostic(Domain11, Klass2D11, R.string._00246),
    Diagnostic(Domain11, Klass2D11, R.string._00044),
    Diagnostic(Domain11, Klass2D11, R.string._00248),
    Diagnostic(Domain11, Klass3D11, R.string._00272),
    Diagnostic(Domain11, Klass3D11, R.string._00138),
    Diagnostic(Domain11, Klass3D11, R.string._00140),
    Diagnostic(Domain11, Klass3D11, R.string._00151),
    Diagnostic(Domain11, Klass3D11, R.string._00139),
    Diagnostic(Domain11, Klass3D11, R.string._00289),
    Diagnostic(Domain11, Klass4D11, R.string._00181),
    Diagnostic(Domain11, Klass4D11, R.string._00180),
    Diagnostic(Domain11, Klass4D11, R.string._00265),
    Diagnostic(Domain11, Klass4D11, R.string._00037),
    Diagnostic(Domain11, Klass5D11, R.string._00218),
    Diagnostic(Domain11, Klass5D11, R.string._00217),
    Diagnostic(Domain11, Klass5D11, R.string._00042),
    Diagnostic(Domain11, Klass6D11, R.string._00007),
    Diagnostic(Domain11, Klass6D11, R.string._00006),
    Diagnostic(Domain11, Klass6D11, R.string._00253),
    Diagnostic(Domain11, Klass6D11, R.string._00280),
    Diagnostic(Domain11, Klass6D11, R.string._00282),
    Diagnostic(Domain11, Klass6D11, R.string._00254),
    Diagnostic(Domain11, Klass6D11, R.string._00008),
    Diagnostic(Domain11, Klass6D11, R.string._00274),

    Diagnostic(Domain12, Klass1D12, R.string._00214),
    Diagnostic(Domain12, Klass1D12, R.string._00183),
    Diagnostic(Domain12, Klass1D12, R.string._00134),
    Diagnostic(Domain12, Klass1D12, R.string._00132),
    Diagnostic(Domain12, Klass1D12, R.string._00133),
    Diagnostic(Domain12, Klass1D12, R.string._00255),
    Diagnostic(Domain12, Klass1D12, R.string._00256),
    Diagnostic(Domain12, Klass2D12, R.string._00214),
    Diagnostic(Domain12, Klass2D12, R.string._00183),
    Diagnostic(Domain12, Klass3D12, R.string._00054),
    Diagnostic(Domain12, Klass3D12, R.string._00053),

    Diagnostic(Domain13, Klass2D13, R.string._00314),
    Diagnostic(Domain13, Klass2D13, R.string._00305),
    Diagnostic(Domain13, Klass2D13, R.string._00315),
    Diagnostic(Domain13, Klass2D13, R.string._00316)
)