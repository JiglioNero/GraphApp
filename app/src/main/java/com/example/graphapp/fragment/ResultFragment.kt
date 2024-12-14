package com.example.graphapp.fragment

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graphapp.R
import com.example.graphapp.data.entity.Point
import com.example.graphapp.ui.recycler.PointsAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.ref.WeakReference


class ResultFragment : Fragment() {

    private var lineChartRef: WeakReference<LineChart>? = null

    companion object {
        private const val ARG_POINTS = "points"
        fun newInstance(points: List<Point>): ResultFragment {
            val f = ResultFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_POINTS, ArrayList(points))
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val pointsList = args.points?.toList()

        val rvPoints = view.findViewById<RecyclerView>(R.id.rvPoints)
        rvPoints.layoutManager = LinearLayoutManager(requireContext())

        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            saveChartImage()
        }

        pointsList?.let { points ->
            val sortedPoints = points.sortedBy { it.x }
            rvPoints.adapter = PointsAdapter(sortedPoints)

            val chartView = view.findViewById<LineChart>(R.id.chart_view)
            lineChartRef = WeakReference(chartView)
            val entries: List<Entry> = sortedPoints.map { Entry(it.x, it.y) }

            val dataSet = LineDataSet(entries, "Пример данных").apply {
                color = Color.BLUE
                valueTextColor = Color.BLACK
                lineWidth = 2f
                setDrawCircles(false)
                setDrawValues(false)
                mode = LineDataSet.Mode.CUBIC_BEZIER
            }

            chartView.data = LineData(dataSet)

            chartView.apply {
                setPinchZoom(true)
                setScaleEnabled(true)
                xAxis.isEnabled = true
                axisLeft.isEnabled = true
                axisRight.isEnabled = false

                description = Description().apply { text = "Сглаженный график" }
                animateX(1000)
            }
        }
    }

    private fun saveChartImage() {
        val graphView = lineChartRef?.get() ?: return
        val bitmap = Bitmap.createBitmap(graphView.width, graphView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        graphView.draw(canvas)

        val filename = "chart_${System.currentTimeMillis()}.png"
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(picturesDir, filename)
        val fos = FileOutputStream(file)
        fos.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        Toast.makeText(requireContext(), "${resources.getString(R.string.image_saved_msg)}: ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
}